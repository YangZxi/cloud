package cn.xiaosm.cloud.core.util.download;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.common.util.SpringContextUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.entity.Range;
import cn.xiaosm.cloud.core.service.ChunkService;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Young
 * @create 2022/12/9
 * @since 1.0.0
 */
@Service
public class DownloadService {
    private static final Long chunkSize = 50 * 1024 * 1024L;
    private static File localPath = new File("C:\\Users\\Young\\Desktop\\cloud", "download");
    private static final ExecutorService pool = Executors.newFixedThreadPool(2);
    private static final ConcurrentHashMap<String, DlTaskInfo> map = new ConcurrentHashMap<>();
    private static final String DOWNLOAD_TASK = "download_task";
    private static final String DOWNLOAD_USER_OF_TASK = "user_";
    private RedisTemplate<String, String> redis;

    @Autowired
    public void setRedis(RedisTemplate<String, String> redis) {
        // redis
        this.redis = redis;
    }

    static {
        if (!localPath.exists()) localPath.mkdirs();
    }

    public DownloadTask build(String name, String url, long userId, int bucketId) {
        return new DownloadTask(name, url, userId, bucketId).setRedis(redis);
    }

    @Data
    @Accessors(chain = true)
    public static class DownloadTask {

        private final Logger logger = LoggerFactory.getLogger(DownloadTask.class);
        private String url;
        private long userId;
        private int bucketId;
        private int taskCount = 0;
        private DlTaskInfo taskInfo;
        private RedisTemplate<String, String> redis;

        protected DownloadTask(String name, String url, long userId, int bucketId) {
            Assert.notBlank(name, "文件名不可以为空");
            Assert.notBlank(url, "文件地址不可以为空");
            this.url = url;
            this.userId = userId;
            this.bucketId = bucketId;
            // 在 redis 中插入数据
            taskInfo = new DlTaskInfo(url);
            taskInfo.setName(name);
            taskInfo.setStatus(0);
            refreshToJava();
        }

        public DlTaskInfo start() {
            this.updateUserOfTask();
            if (!this.addTask()) {
                refreshToJava();
                logger.info("任务已存在：{}, 已完成：{}, 总数：{}", taskInfo.getHash(), taskInfo.getFinishedSize(), taskInfo.getTotalChunkCount());
                if (taskInfo.isFinished()) {
                    logger.info("任务 {} 已完成，直接转存", taskInfo.getHash());
                    SpringContextUtils.getBean(ChunkService.class).integrateDownloadFile(taskInfo);
                }
                return taskInfo;
            }
            // 检查是否支持分段下载
            Range range = null;
            try {
                range = acceptSplit(url);
                logger.info("{}", range);
            } catch (IOException e) {
                return null;
            }
            int i = 0;
            if (range.isPart()) {
                int part = (int) (range.getTotal() / chunkSize);
                int chunkCount = (range.getTotal() % chunkSize == 0) ? part : part + 1;
                for (; i < part; i++) {
                    Range r = new Range(i * chunkSize, chunkSize, range.getTotal(), i);
                    pool.submit(createTask(url, r, i + 1));
                }
                if (chunkCount > part) {
                    pool.submit(createTask(url, new Range(part * chunkSize, range.getTotal(), part), i + 1));
                }
            }
            taskCount = i + 1;
            logger.info("任务：{}， 总长度：{}, 子任务数：{}", url, range.getTotal(), taskCount);
            taskInfo.setStatus(1);
            taskInfo.setTotalLength(range.getTotal());
            taskInfo.setTotalChunkCount(taskCount);
            this.refreshToRedis();
            return taskInfo;
        }

        /**
         * 保存任务详细信息至缓存
         *
         * @return
         */
        private synchronized boolean addTask() {
            String key = taskInfo.getHash();
            // 如果缓存中，此任务信息存在，则不添加
            if (Boolean.TRUE.equals(CacheUtils.hasKey(key))) {
                return false;
            }
            CacheUtils.set(key, taskInfo);
            map.put(taskInfo.getHash(), taskInfo);
            return true;
        }

        /**
         * 将缓存中的任务信息刷新至java
         */
        private void refreshToJava() {
            DlTaskInfo info = map.get(taskInfo.getHash());
            if (info == null) {
                info = (DlTaskInfo) CacheUtils.get(taskInfo.getHash());
                if (info != null) {
                    taskInfo = info;
                }
            } else {
                taskInfo = info;
            }
        }

        /**
         * 将缓存中的任务信息刷新至redis
         */
        private boolean refreshToRedis() {
            synchronized (taskInfo.getHash()) {
                return CacheUtils.set(taskInfo.getHash(), taskInfo);
            }
        }

        /**
         * 更新当前用户的离线下载任务列表
         * 结构：hash: [userId, userId]
         */
        private void updateUserOfTask() {
            SetOperations<String, String> ops = redis.opsForSet();
            Long re = ops.add(taskInfo.userOfTaskName(), userId + "_" + bucketId);
            // 如果用户的缓存中存在此任务，则不添加
            if (re == null || re == 0) {
                throw new ResourceException("请勿重复创建任务");
            }
        }

        private void removeUserOfTask() {
            SetOperations<String, String> ops = redis.opsForSet();
            Long re = ops.remove(taskInfo.userOfTaskName(), userId + "_" + bucketId);
        }

        /**
         * 通过设置 Range 头请求来判断服务器是否支持分段下载
         *
         * @param url
         * @return
         * @throws IOException
         */
        private Range acceptSplit(String url) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            // 下载 10byte 数据测试一下
            connection.setRequestProperty("Range", "bytes=0-9");
            connection.setReadTimeout(3000);
            connection.connect();
            connection.disconnect();
            if (connection.getResponseCode() == 206 && connection.getContentLength() == 10) {
                return Range.build(connection.getHeaderField("Content-Range"));
            } else {
                return new Range(connection.getContentLengthLong());
            }
        }

        private Thread createTask(String url, Range range, int order) {
            logger.info("创建【分块】任务：{}, {}, 分块：{}", url, range, order);
            return new Thread(() -> {
                File file = null;
                try {
                    long contentLength = 0;
                    HttpURLConnection connection = null;
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestProperty("Range", "bytes=" + range.getStart() + "-" + range.getEnd());
                    connection.connect();
                    contentLength = connection.getContentLengthLong();
                    int code = connection.getResponseCode();
                    if (code >= 400) {
                        logger.error("连接目标资源失败，响应码：{}", code);
                    }
                    // connection.getHeaderFields().forEach((key, list) -> {
                    //     System.out.println(key + ": " + list.stream().toList());
                    // });
                    // // 获取文件名
                    // String disposition = connection.getHeaderField("Content-Disposition");
                    // System.out.println("文件名：" + disposition);
                    // System.out.println("响应体长度：" + contentLength + "/ " + contentLength / 1024 / 1024 + "MB");
                    // System.out.println("===================");
                    // 转存文件
                    file = new File(localPath, IdUtil.simpleUUID() + ".data");
                    file.createNewFile();

                    RandomAccessFile writer = new RandomAccessFile(file, "rw");
                    InputStream in = connection.getInputStream();
                    int len = 0;
                    byte[] buff = new byte[1024];
                    float rate = 0;
                    int temp = 0;
                    int progress = 0;
                    long now = System.currentTimeMillis();
                    int millis = 1000;
                    while ((len = in.read(buff)) > 0) {
                        writer.write(buff, 0, len);
                        progress += len;
                        if (System.currentTimeMillis() - now >= millis) {
                            rate = (float) progress / contentLength;
                            taskInfo.addProgress(progress - temp);
                            System.out.printf("当前进度：%s - %.2f%%, 总进度：%.2f%%\n", order, rate * 100, taskInfo.progress());
                            millis += 1000;
                            temp = progress;
                        }
                    }
                    taskInfo.addProgress(progress - temp);
                    // 判断文件长度是否相等
                    if (writer.length() != contentLength) {
                        taskInfo.setStatus(0);
                        refreshToRedis();
                        throw new IOException("文件传输异常");
                    }
                    in.close();
                    connection.disconnect();
                    writer.close();
                    logger.info("【分块】任务完成：{}, 分块：{}", url, order);
                    // 存储 chunk 信息
                    DlChunk chunk = new DlChunk();
                    chunk.setType(1);
                    chunk.setOrder(order);
                    chunk.setTotal(taskCount);
                    chunk.setUserId(userId);
                    chunk.setPath(file.getPath());
                    taskInfo.addChunk(chunk);
                    refreshToRedis();
                    if (taskInfo.isFinished()) {
                        System.out.println("传输完成");
                        SpringContextUtils.getBean(ChunkService.class).integrateDownloadFile(taskInfo);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    removeUserOfTask();
                }
            });
        }
    }
}
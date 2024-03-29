package cn.xiaosm.cloud.core.service;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.util.FileUtil;
import cn.xiaosm.cloud.common.util.SpringContextUtils;
import cn.xiaosm.cloud.common.util.StringUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.entity.Bucket;
import cn.xiaosm.cloud.core.entity.Chunk;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.response.UploadDTO;
import cn.xiaosm.cloud.core.mapper.ChunkMapper;
import cn.xiaosm.cloud.core.storage.UploadConfig;
import cn.xiaosm.cloud.core.util.download.DlChunk;
import cn.xiaosm.cloud.core.util.download.DlTaskInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author Young
 * @create 2022/12/13
 * @since 1.0.0
 */
@Slf4j
@Service
public class ChunkService {

    @Autowired
    ChunkMapper chunkMapper;
    @Autowired
    RedisTemplate<String, String> redis;
    @Autowired
    ResourceService resourceService;

    @Transactional
    public boolean save(UploadDTO dto) {
        // 检查上传的文件名
        // this.checkNameAndUnique(file.getOriginalFilename(), parentId, bucket.getId()); // 与下面的判断重复
        if (!dto.getCurrentChunkSize().equals(dto.getFile().getSize())) {
            log.error("文件大小不一致");
            return false;
        }
        String hash;
        try {
            hash = DigestUtil.md5Hex(dto.getFile().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // 当前分块没有上传
        if (chunkMapper.existByHash(hash) == null) {
            Chunk chunk = new Chunk();
            chunk.setSize(dto.getCurrentChunkSize());
            chunk.setOrder(dto.getChunkNumber());
            chunk.setTotal(dto.getTotalChunks());
            chunk.setFileHash(dto.getIdentifier());
            chunk.setType(2);
            chunk.setHash(hash);
            String fileName = hash + ".data";
            // 数据库存储失败
            if (chunkMapper.insert(chunk) != 1) {
                return false;
            }
            File dest = new File(UploadConfig.CHUNK_PATH, fileName);
            try {
                log.info("{}-分块保存成功，{}", dto.getIdentifier(), dest.getAbsolutePath());
                // 缓存文件存在 && 大小相等直接返回成功
                if (dest.exists() && dest.length() == dto.getCurrentChunkSize()) {
                    return true;
                }
                dto.getFile().transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 合并分块
     */
    public boolean integrateFile(UploadDTO dto, Bucket bucket, Long parentId) {
        List<Chunk> chunks = chunkMapper.listByFileHash(dto.getIdentifier());
        if (chunks.isEmpty()) {
            return false;
        }
        // 检查所有分块是否都上传完成
        if (chunks.size() != chunks.get(0).getTotal()) {
            // 没有上传完，或者上传完，数据已经删除了
            return false;
        }
        // 合并之前，检查当前用户的仓库中是否文件存在
        Resource db = SpringContextUtils.getBean(ResourceService.class)
            .getAndCheckHashInPath(dto.getIdentifier(), dto.getFilename(), parentId, bucket.getId());
        if (db != null) {
            log.error("当前目录下已有相同文件-{}", db.getName());
            return false;
        }

        String suffixPath = ResourceService.suffixPath();
        String filename = dto.getIdentifier() + "." + FileUtil.extName(dto.getFilename());
        File dest = this.integrateFile(chunks, filename);

        // 写完以后保存至数据库
        Resource resource = new Resource();
        resource.setPath(suffixPath + "/" + filename);
        resource.setHash(dto.getIdentifier());
        resource.setName(dto.getFilename());
        resource.setBucketId(bucket.getId());
        resource.setUserId(bucket.getUserId());
        resource.setParentId(parentId);
        resource.setDir(false);
        resource.setSize(dest.length());
        resource.setType(FileUtil.getType(dest));
        SpringContextUtils.getBean(ResourceService.class).save(resource);
        // 删除所有chunk，注：故意这么写的，为了不走事物，即时生效 // 先不删除，后期考虑怎么处理
        this.deleteByIds(chunks.stream().map(Chunk::getId).toList());
        log.info("分块上传，文件保存成功，文件id：{}", resource.getId());
        return true;
    }

    @Transactional
    public void integrateDownloadFile(DlTaskInfo taskInfo) {
        // 没有扩展名，需要判断类型后添加后缀
        String filename = IdUtil.simpleUUID();
        String suffix = FileNameUtil.getSuffix(taskInfo.getName());
        if (StrUtil.isNotBlank(suffix)) {
            filename = filename + "." + suffix;
        }
        // 合并文件
        File file = integrateFile(taskInfo.getFinishedChunks(), filename);
        String type = FileUtil.getType(file);
        if (StrUtil.isNotBlank(type)) {
            String originName = filename;
            filename = IdUtil.simpleUUID() + "." + type;
            File newFile = new File(file.getParentFile(), filename);
            Assert.isTrue(file.renameTo(newFile));
            file = newFile;
            log.info("文件：{} 重命名为：{}", originName, filename);
        }
        String path;
        if (taskInfo.getPath() != null) {
            path = taskInfo.getPath();
        } else {
            path = file.getParentFile().getName() + "/" + filename;
            taskInfo.setPath(path);
            synchronized (taskInfo.getHash()) {
                CacheUtils.set(taskInfo.getHash(), taskInfo);
            }
        }
        // 在数据库中插入文件
        Set<String> userIds = redis.opsForSet().members(taskInfo.userOfTaskName());
        if (userIds == null || userIds.size() == 0) {
            log.info("需要保存的用户列表为空，{}", taskInfo.userOfTaskName());
            return;
        }
        log.info("需要写入数据的bucket个数：{}", userIds.size());
        long size = file.getTotalSpace();
        String hash = MD5.create().digestHex(file);
        for (String s : userIds) {
            String[] ss = s.split("_");
            if (ss.length != 2) continue;
            Resource r = new Resource();
            StringBuilder realName = new StringBuilder(taskInfo.getName());
            long userId = Long.parseLong(ss[0]);
            int bucketId = Integer.parseInt(ss[1]);
            Resource downloadDir = resourceService.createDownloadDir(userId, bucketId);
            log.info("检查在 download 下是否有重名文件");
            // 如果是根目录，需要根据 bucketId 和 parentId 来进行检索
            List<String> names =  resourceService.list(new QueryWrapper<Resource>()
                .eq("bucket_id", bucketId)
                .eq("parent_id", downloadDir.getId())
                .select("name"))
                .stream().map(Resource::getName).toList();
            while (StringUtils.contains(names, realName.toString())) {
                int dot = realName.lastIndexOf(".");
                if (dot != -1) {
                    realName.insert(dot, "-副本");
                } else {
                    realName.append("-副本");
                }
            }
            r.setName(realName.toString());
            r.setPath(path);
            r.setUserId(Long.parseLong(ss[0]));
            r.setBucketId(bucketId);
            r.setParentId(downloadDir.getId());
            r.setSize(size);
            r.setType(type);
            r.setHash(hash);
            log.info("{} 将保存至bucket {}，文件名：{}", (taskInfo.getHash()), bucketId, realName);
            resourceService.save(r);
            redis.opsForSet().remove(taskInfo.userOfTaskName(), s);
        }
    }

    private File integrateFile(List<? extends Chunk> chunks, String filename) {
        chunks.sort(Comparator.comparingInt(Chunk::getOrder));
        File dest = ResourceService.transformFile(filename);
        try (RandomAccessFile raf = new RandomAccessFile(dest, "rw")) {
            for (Chunk chunk : chunks) {
                File temp;
                if (chunk instanceof DlChunk dlChunk) {
                    temp = new File(dlChunk.getPath());
                } else {
                    temp = new File(UploadConfig.CHUNK_PATH, chunk.getHash() + ".data");
                }
                if (!temp.exists()) {
                    log.error("文件 chunk 不存在：{}", temp.getAbsolutePath());
                    throw new CanShowException("文件 chunk 不存在", 400);
                }
                integrateFile(raf, new FileInputStream(temp));
            }
            log.info("文件整合完成：{}，{}", filename, dest.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CanShowException("文件整合出现问题", 400);
        }
        return dest;
    }

    private void integrateFile(RandomAccessFile raf, InputStream in) throws IOException {
        byte[] buff = new byte[4096];
        int len;
        while ((len = in.read(buff)) != -1) {
            raf.write(buff, 0, len);
        }
        in.close();
    }

    public Chunk getByFileHash(String fileHash) {
        return chunkMapper.selectByFileHash(fileHash);
    }

    public int deleteByIds(Collection<Integer> ids) {
        return chunkMapper.deleteBatchIds(ids);
    }

    public Integer[] getUploaded(String hash) {
        return chunkMapper.listOrderByFileHash(hash);
    }

}
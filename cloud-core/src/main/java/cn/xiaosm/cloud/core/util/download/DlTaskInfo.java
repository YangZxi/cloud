package cn.xiaosm.cloud.core.util.download;

import cn.hutool.crypto.digest.MD5;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Young
 * @create 2023/4/2
 * @since 1.0.0
 */
@Data
public class DlTaskInfo implements Serializable {

    private String hash;
    private String name;
    private String url;
    /**
     * 资源总长度
     */
    private long totalLength;
    /**
     * 以传输长度
     */
    private AtomicLong currentLength = new AtomicLong(0);
    /**
     * 总分块数
     */
    private int totalChunkCount;
    /**
     * 已完成分块
     */
    private List<DlChunk> finishedChunks = new LinkedList<>();
    private String path;
    /**
     * 状态: 0暂停，1运行，2完成
     */
    private int status;
    private boolean finished = false;

    protected DlTaskInfo(String url) {
        this.url = url;
        this.hash = "dl_task_" + MD5.create().digestHex(url);
    }

    public void addChunk(DlChunk chunk) {
        finishedChunks.add(chunk);
        if (finishedChunks.size() == totalChunkCount) {
            this.finished = true;
        }
    }

    public int getFinishedSize() {
        return finishedChunks.size();
    }

    public void addProgress(int v) {
        currentLength.addAndGet(v);
    }

    /**
     * 获取下载进度
     * @return
     */
    public float progress() {
        return ((float) currentLength.get() / totalLength) * 100;
    }

    public String userOfTaskName() {
        return "user_" + hash;
    }

}
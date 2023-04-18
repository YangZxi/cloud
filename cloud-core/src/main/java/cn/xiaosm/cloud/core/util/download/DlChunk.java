package cn.xiaosm.cloud.core.util.download;

import cn.xiaosm.cloud.core.entity.Chunk;
import lombok.Data;

/**
 * @author Young
 * @create 2023/4/2
 * @since 1.0.0
 */
@Data
public class DlChunk extends Chunk {

    private String path;
    private float progress;

    public void setProgress(float progress) {
        this.progress = progress > 100 ? 100 : progress;
    }

    public boolean finished() {
        return progress == 100;
    }

}
package cn.xiaosm.cloud.core.entity.dto;

import cn.xiaosm.cloud.core.entity.Resource;
import lombok.Data;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Data
public class ResourceDTO extends Resource {

    private String bucketName;
    // 文件绝对路径
    private String fileAbPath;
    private String content;
    // 目标资源 id
    private Long targetId;
    private String url;

    @Override
    public String getPath() {
        return ("/" + (super.getPath() == null ? "" : super.getPath())).replaceAll("/+|\\+", "/");
    }
    
}
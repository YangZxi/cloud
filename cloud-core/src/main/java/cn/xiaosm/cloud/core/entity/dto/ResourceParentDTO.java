package cn.xiaosm.cloud.core.entity.dto;

import cn.xiaosm.cloud.core.entity.Resource;
import lombok.Data;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Data
public class ResourceParentDTO extends Resource {

    private int level;
    private String viewPath;

}
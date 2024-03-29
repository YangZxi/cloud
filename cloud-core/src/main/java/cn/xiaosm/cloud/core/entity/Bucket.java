package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.core.admin.entity.BaseEntity;
import cn.xiaosm.cloud.core.entity.enums.BucketType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文件存储仓库
 *
 * @author Young
 * @create 2022/4/5
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("bucket")
public class Bucket extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /* 仓库名称 */
    private String name;

    /* 仓库中文名称 */
    private String zhName;

    /* 仓库路径 | 在数据库中存储的是根路径 */
    private String path = "";

    /* 仓库归属权 */
    private Long userId;

    @TableField(exist = false)
    private BucketType type;

    public Bucket setPath(String path) {
        if (path == null) return this;
        this.path = path.replaceAll("/+|\\\\+", "/");
        return this;
    }

}
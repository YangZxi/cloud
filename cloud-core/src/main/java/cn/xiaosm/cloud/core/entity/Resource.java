package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.core.admin.entity.BaseEntity;
import cn.xiaosm.cloud.core.config.EditableType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@TableName("resource")
public class Resource extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    // 文件名
    private String name;
    // 文件保存的路径名，包含文件名和扩展名
    private String path;
    // 文件uuid
    private String hash;
    // 文件大小，单位字节
    private Long size;
    // 文件类型
    private String type;
    private Long userId;
    private Integer bucketId;
    @JsonIgnore
    private Long refId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;
    private String cdn;
    @TableField(exist = false)
    private boolean dir = false;

    /**
     * 是否可编辑
     */
    @TableField(exist = false)
    private boolean edit = false;

    public Resource() { }

    public Resource(Bucket bucket) {
        this.userId = bucket.getUserId();
        this.bucketId = bucket.getId();
    }

    public Resource setName(String name) {
        this.name = name == null ? "unset-name." + this.type  : name;
        return this;
    }

    public Resource setType(String type) {
        this.type = type;
        this.dir = type.equalsIgnoreCase("DIR");
        // 判断文件是否可编辑
        this.edit = EditableType.isEditable(type);
        return this;
    }

    public void setPath(String path) {
        this.path = path == null ? "" : path.replaceAll("/+|\\+", "/");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) && Objects.equals(name, resource.name) && Objects.equals(path, resource.path) && Objects.equals(hash, resource.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, hash);
    }
}
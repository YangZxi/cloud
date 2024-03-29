package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.core.config.jackson.JacksonConfig;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class Share {

    @TableId(type = IdType.AUTO)
    private Integer autoId;

    private String id;

    /**
     * 短链接
     */
    private String shortCode;

    @JsonSerialize(using = JacksonConfig.LocalDateTimeSerializer.class)
    @JsonDeserialize(using = JacksonConfig.LocalDateTimeDeserializer.class)
    private LocalDateTime deadline;

    private String password;

    private int viewCount;

    private int downloadCount;

    private String resourceIds;

    private Long userId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = JacksonConfig.LocalDateTimeSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createTime;

    public Share(Share share) {
        this.autoId = share.getAutoId();
        this.id = share.getId();
        this.shortCode = share.getShortCode();
        this.deadline = share.getDeadline();
        this.password = share.getPassword();
        this.viewCount = share.getViewCount();
        this.downloadCount = share.getDownloadCount();
        this.resourceIds = share.getResourceIds();
        this.userId = share.getUserId();
        this.createTime = share.getCreateTime();
    }
}
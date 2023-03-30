/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: Operator
 * Author:   Young
 * Date:     2020/6/16 10:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class Log implements Serializable {

    private String title;
    private String content;
    private String error;
    private String type;
    private String method;
    private String ip;
    private Integer time;
    private Long userId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private LocalDateTime createTime;

}
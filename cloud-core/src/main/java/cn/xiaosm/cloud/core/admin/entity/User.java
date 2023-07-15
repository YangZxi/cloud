/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: User
 * Author:   Young
 * Date:     2020/6/13 14:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.entity;

import cn.xiaosm.cloud.core.admin.entity.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "user")
public class User extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    @JsonIgnore
    private String uuid;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String avatar;
    private String email;
    private StatusEnum status;

    public User() {}

    public User(Long id, String uuid) {
        this.id = id;
        this.uuid = uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? "" : uuid;
    }

}
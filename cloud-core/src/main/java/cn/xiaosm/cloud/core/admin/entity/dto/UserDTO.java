/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: UserDTO
 * Author:   Young
 * Date:     2020/6/14 9:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.entity.dto;

import cn.xiaosm.cloud.core.admin.entity.Menu;
import cn.xiaosm.cloud.core.admin.entity.Role;
import cn.xiaosm.cloud.core.admin.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
@Data
public class UserDTO extends User implements Serializable {

    @JsonIgnore
    private String password;
    private String roleIds;
    private List<Role> roles;
    private List<Menu> menus;

}
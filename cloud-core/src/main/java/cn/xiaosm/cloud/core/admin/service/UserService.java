/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: UserService
 * Author:   Young
 * Date:     2020/6/13 14:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.service;

import cn.xiaosm.cloud.core.admin.entity.User;
import cn.xiaosm.cloud.core.admin.entity.UserLoginTrack;
import cn.xiaosm.cloud.core.admin.entity.UserOpen;
import cn.xiaosm.cloud.core.admin.entity.dto.UserDTO;
import cn.xiaosm.cloud.core.admin.entity.enums.UserOpenType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseService<User> {

    List<UserOpen> getUserOpenByUserId(Long userId);

    boolean addUserOpen(UserOpen userOpen);

    @Transactional
    void updateUserRoles(Long userId, Set<Integer> roleIds);

    boolean modPassword(User user);

    boolean modPassword(User user, String password);

    boolean modPassword(User user, String password, boolean keep);

    String getDefaultPassword();

    int removeById(Integer id);

    int removeByIds(Set<Long> ids);

    UserDTO getByUsername(String username);

    UserDTO getByUsername(String openId, UserOpenType loginType);

    User login(User user);

    List<UserLoginTrack> listOfTrack(Long userId, Integer size);

    boolean addLoginTrack(Long userId, String ip);

    boolean revokeUserOpen(UserOpen userOpen);
}
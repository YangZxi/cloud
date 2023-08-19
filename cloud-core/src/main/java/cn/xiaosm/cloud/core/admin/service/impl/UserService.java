package cn.xiaosm.cloud.core.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.core.admin.entity.User;
import cn.xiaosm.cloud.core.admin.entity.UserOpen;
import cn.xiaosm.cloud.core.admin.entity.dto.UserDTO;
import cn.xiaosm.cloud.core.admin.entity.enums.UserOpenType;
import cn.xiaosm.cloud.core.admin.entity.vo.Pager;
import cn.xiaosm.cloud.core.admin.entity.vo.UserVO;
import cn.xiaosm.cloud.core.admin.mapper.UserMapper;
import cn.xiaosm.cloud.core.admin.mapper.UserOpenMapper;
import cn.xiaosm.cloud.core.admin.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements BaseService<User> {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserOpenMapper userOpenMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String DEFAULT_PASS = "123456";

    @Override
    @Transactional
    public boolean updateById(User user) {
        // if (this.isExist(user)) throw new SQLOperateException("用户名" + user.getUsername() + "已存在");
        user.setUsername(null);
        int i = userMapper.updateById(user);
        if (Objects.nonNull( ((UserVO) user).getRoleIds() )) {
            // 先清除所有的角色信息
            this.removeUserRoles(user.getId());
            // 在进行新的插入
            this.addUserRoles(user.getId(), ((UserVO) user).getRoleIds());
        }
        return i == 1;
    }

    @Transactional
    public void updateUserRoles(Long userId, Set<Integer> roleIds) {
        this.removeUserRoles(userId);
        this.addUserRoles(userId, roleIds);
    }

    @Override
    @Transactional
    public boolean save(User user) {
        if (this.isExist(user)) throw new SQLOperateException("用户名" + user.getUsername() + "已存在");
        // 设置默认密码
        user.setPassword(getDefaultPassword());
        userMapper.insert(user);
        this.addUserRoles(user.getId(), ((UserVO) user).getRoleIds());
        return true;
    }

    public boolean isExist(@NotNull User user) {
        if (StrUtil.isBlank(user.getUsername())) return false;
        return userMapper.selectOne(
            new QueryWrapper<User>()
                .eq("username", user.getUsername())
        ) != null;
    }

    /**
     * 修改密码，根据keep决定是否进行数据库操作
     */
    public boolean updatePassword(User user, String rawPassword) {
        User newU = new User();
        newU.setId(user.getId());
        user.setPassword(encodePassword(rawPassword));
        return userMapper.updateById(user) == 1;
    }

    /**
     * 获取使用默认密码
     */
    public String getDefaultPassword() {
        return bCryptPasswordEncoder.encode(DEFAULT_PASS);
    }

    public String encodePassword(String password) {
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean equalsPassword(String password, User user) {
        return bCryptPasswordEncoder.matches(
                bCryptPasswordEncoder.encode(password),
                user.getPassword()
        );
    }

    /**
     * 删除用户的所有角色信息
     */
    public void removeUserRoles(Long userId) {
        userMapper.deleteUserRole(userId);
    }

    /**
     * 添加用户的角色
     */
    public void addUserRoles(Long userId, Set<Integer> roleIds) {
        for (Integer roleId : roleIds) {
            try {
                userMapper.insertUserRole(userId, roleId);
            } catch (Exception e) {
                throw new SQLOperateException("保存失败###选择角色不存在");
            }
        }
    }

    public int removeByIds(Set<Long> ids) {
        int count = 0;
        for (Long id : ids) {
            if (id == 1 || id == 2) {
                throw new SQLOperateException("系统保留数据，请勿操作");
            }
            userMapper.deleteUserRole(id);
            this.removeById(id);
            count++;
        }
        return count;
    }

    /**
     * 根据用户名查询用户信息
     */
    public UserDTO getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 通过快捷方式登录
     */
    public UserDTO getByUsername(String openId, UserOpenType loginType) {
        return userMapper.selectByOpenId(openId, loginType);
    }

    @Override
    public Pager<User> listOfPage(Pager<User> pager, QueryWrapper<User> queryWrapper) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return userMapper.selectPage(pager, queryWrapper);
    }

    public List<UserOpen> getUserOpenByUserId(Long userId) {
        return userOpenMapper.selectList(new QueryWrapper<UserOpen>().eq("user_id", userId));
    }

    public boolean addUserOpen(UserOpen userOpen) {
        // 查询当前的UserOpen信息是否已经被绑定
        if (!isUnbind(userOpen))
            throw new CanShowException(StrUtil.format("当前{}账号已被绑定", userOpen.getType()));
        // 根据平台类型查询当前用户是否有重复的信息
        UserOpen userDB = userOpenMapper.selectOne(
            new QueryWrapper<UserOpen>()
                .eq("user_id", userOpen.getUserId())
                .eq("type", userOpen.getType())
        );
        if (userDB != null) {
            userOpen.setId(userDB.getId()).clearTime();
            return userOpenMapper.updateById(userOpen) == 1;
        }
        return userOpenMapper.insert(userOpen) == 1;
    }

    /**
     * 查询当前openId是否没有被绑定
     */
    public boolean isUnbind(UserOpen userOpen) {
        UserOpen open = userOpenMapper.selectOne(new QueryWrapper<UserOpen>()
            .eq("open_id", userOpen.getOpenId())
            .eq("type", userOpen.getType()));
        /*
         * 数据库不存在
         * 数据库已存在的用户Id与需要绑定的用户id相同
         */
        return open == null || Objects.equals(open.getUserId(), userOpen.getUserId());
    }

    /**
     * 删除用户的open信息
     */
    public boolean revokeUserOpen(UserOpen userOpen) {
        return userOpenMapper.deleteById(userOpen) == 1;
    }

}
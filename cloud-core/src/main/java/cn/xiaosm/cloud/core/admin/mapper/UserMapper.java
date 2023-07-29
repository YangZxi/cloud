package cn.xiaosm.cloud.core.admin.mapper;

import cn.xiaosm.cloud.core.admin.entity.User;
import cn.xiaosm.cloud.core.admin.entity.dto.UserDTO;
import cn.xiaosm.cloud.core.admin.entity.enums.UserOpenType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户登录查询的 sql
     * 返回数据包括用户基本信息、所对应的角色信息和最近1条登录记录
     */
    @Select("SELECT u.* " +
            " FROM `user` u " +
            " WHERE u.username = #{username} AND u.password = #{password}")
    @Results(id = "userRoleMap", value = {
            // 配置id 避免无法注入进id属性
            @Result(id = true, property = "id", column = "id"),
            // 查询角色信息
            @Result(property = "roles", column = "id",
                    many = @Many(select = "cn.xiaosm.cloud.core.admin.mapper.RoleMapper.selectIdAndNameByUserId")),
    })
    UserDTO selectByUsernameAndPassword(User user);

    /**
     * 通过用户名查询用户信息
     */
    @Select("SELECT u.* FROM `user` u " +
            " WHERE u.username = #{username}")
    @ResultMap(value = "userRoleMap")
    UserDTO selectByUsername(String username);

    /**
     * qq快捷登录
     */
    @Select("SELECT u.* FROM `user` u LEFT JOIN `user_open` o ON u.id = o.user_id" +
            " WHERE o.type = #{type} AND o.open_id = #{openId}")
    @ResultMap(value = "userRoleMap")
    UserDTO selectByOpenId(@Param("openId") String openId, @Param("type") UserOpenType type);

    /**
     * 将用户状态改为已删除
     */
    @Update("UPDATE `user` SET `status` = 2 WHERE `id` = #{userId}")
    int updateUserStatusToDeleted(Integer userId);

    /**
     * 删除所有的用户角色信息
     */
    @Delete("DELETE FROM `user_role` WHERE `user_id` = #{userId}")
    void deleteUserRole(Long userId);

    /**
     * 插入用户角色信息
     */
    @Insert("INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (#{userId}, #{roleId})")
    int insertUserRole(Long userId, Integer roleId);

}
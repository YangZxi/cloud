package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.core.admin.service.impl.UserService;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.security.annotation.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Young
 * @create 2022/10/13
 * @since 1.0.0
 */
@Api("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * (已加密)
     * 根据 Token 获取当前登录用户的信息
     */
    @GetMapping("info")
    @LogRecord("获取用户信息")
    @Encrypt
    public RespBody userInfo() {
        return RespUtils.success(SecurityUtils.getLoginUser());
    }

    public record UpdatePassword(String newPassword) {}

    @PostMapping("password")
    @LogRecord("修改密码")
    public RespBody password(@RequestBody UpdatePassword updatePassword) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if ("guest".equals(loginUser.getUsername())) {
            log.error("guest 用户不允许修改密码");
            throw new CanShowException("密码修改失败");
        }
        userService.updatePassword(loginUser, updatePassword.newPassword);
        return RespUtils.success("修改密码成功");
    }

}
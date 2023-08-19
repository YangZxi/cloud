package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.common.exception.LoginException;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.security.DefaultSecurityUtils;
import cn.xiaosm.cloud.security.entity.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils extends DefaultSecurityUtils {

    public static AuthUser getUser() {
        return (AuthUser) getAuthentication().getPrincipal();
    }

    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Long getLoginUserId() {
        return SecurityUtils.getLoginUser().getId();
    }

    public static Authentication getAuthentication() {
        try {
            return SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception e) {
            throw new LoginException("登录信息过期");
        }
    }

    public static boolean isLogin() {
        return SecurityUtils.getAuthentication().isAuthenticated();
    }
}
package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.security.DefaultSecurityUtils;
import cn.xiaosm.cloud.security.entity.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@Slf4j
public class SecurityUtils extends DefaultSecurityUtils {

    public static AuthUser getUser() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof AuthUser authUser) {
            return authUser;
        } else {
            return null;
        }
    }

    public static LoginUser getLoginUser() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof LoginUser loginUser) {
            return loginUser;
        } else {
            return null;
        }
    }

    public static Long getLoginUserId() {
        return Objects.requireNonNull(SecurityUtils.getLoginUser()).getId();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isLogin() {
        return SecurityUtils.getAuthentication().isAuthenticated();
    }
}
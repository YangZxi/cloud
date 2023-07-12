package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.common.exception.LoginException;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.security.DefaultSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils extends DefaultSecurityUtils {

    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new LoginException("登录信息过期");
        }
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

}
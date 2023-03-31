package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.security.annotation.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Young
 * @create 2022/10/13
 * @since 1.0.0
 */
@Api("user")
public class UserController {

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

}
package cn.xiaosm.cloud.core.controller;

import cn.hutool.core.util.IdUtil;
import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.core.admin.entity.dto.LoginUserDTO;
import cn.xiaosm.cloud.core.admin.service.RoleService;
import cn.xiaosm.cloud.core.admin.service.impl.UserService;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.config.security.service.UserDetailsServiceImpl;
import cn.xiaosm.cloud.security.annotation.Decrypt;
import cn.xiaosm.cloud.security.entity.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Api
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticationManagerBuilder managerBuilder;
    @Autowired
    TokenService tokenService;
    @Autowired
    RoleService roleService;

    private static LoginUser GUEST_USER = null;

    @PostMapping("login")
    public RespBody login(@Decrypt LoginUserDTO user, HttpServletResponse response) {
        if ("guest".equals(user.getUsername()) && null != GUEST_USER) {
            return publicLogin(user, response);
        }
        /* START
          该方法会去调用 UserDetailService 的方法
          由于我创建了此类的实现类，所以会去调用我自定义的登录逻辑，从后台获取 User 的信息
          密码的校验 security 会帮我们处理，如果密码错误，会抛出异常
         */
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = managerBuilder
            .getObject().authenticate(authenticationToken);
        /* END */

        // 如果密码没有输入错误，将能够正确执行以下代码
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 设置登录用户信息（用户的权限和菜单列表）
        userDetailsService.loadUserInfo(loginUser);
        // 根据认证创建 Token
        String token;
        if ("guest".equals(loginUser.getUsername())) {
            GUEST_USER = loginUser;
            token = tokenService.createToken(IdUtil.simpleUUID(), TokenType.LOGIN, loginUser);
        } else {
            token = tokenService.createToken(loginUser);
        }
        // 返回 token
        RespUtils.sendToken(response, token);
        return null;
    }

    private RespBody publicLogin(LoginUserDTO user, HttpServletResponse response) {
        if (userService.equalsPassword(user.getPassword(), GUEST_USER)) {
            RespUtils.sendToken(response, tokenService.createToken(IdUtil.simpleUUID(), TokenType.LOGIN, GUEST_USER));
            return null;
        } else {
            return RespUtils.fail("用户名或密码不正确");
        }
    }

}
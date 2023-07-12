package cn.xiaosm.cloud.core.config.security.service;

import cn.hutool.core.util.IdUtil;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.entity.ShareUser;
import cn.xiaosm.cloud.security.entity.TokenType;
import cn.xiaosm.cloud.security.service.DefaultTokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
@Log4j2
@Component("tokenService")
public class TokenService extends DefaultTokenService {

    /**
     * 创建 Token
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser) {
        String loginId = IdUtil.simpleUUID();
        loginUser.setLoginId(loginId);
        return super.createToken(loginId, TokenType.LOGIN, loginUser);
    }

    public String createShareToken(String shareId) {
        String uuid = IdUtil.simpleUUID();
        Date expireTime = new Date(System.currentTimeMillis() + super.getEXPIRES());
        String token = JWT.create()
            .withAudience(shareId)
            .withClaim("TYPE", TokenType.SHARE.name())
            .withClaim("shareId", shareId)
            .withClaim(JWT_CLAIM_UUID, uuid)
            .withExpiresAt(expireTime)
            .sign(Algorithm.HMAC256(super.getSECRET_KEY()));
        ShareUser shareUser = new ShareUser(shareId);
        shareUser.setExpireTime(expireTime);
        CacheUtils.set(uuid, shareUser, shareUser.expired());
        return token;
    }

    public LoginUser getLoginUser() {
        return this.getLoginUser(ServletUtils.getRequest());
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        AuthUser authUser = super.getAuthUser(request);
        if (authUser instanceof LoginUser) return (LoginUser) authUser;
        return null;
    }

    public TokenType getType(HttpServletRequest request) {
        String type = super.getClaim(request, "TYPE");
        return TokenType.valueOf(type);
    }

}
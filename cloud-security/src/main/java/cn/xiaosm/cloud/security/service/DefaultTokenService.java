package cn.xiaosm.cloud.security.service;

import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.factory.YamlSourceFactory;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.entity.TokenType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Data
@Log4j2
@Component("defaultTokenService")
@PropertySource(value = "classpath:security.yml", factory = YamlSourceFactory.class)
public class DefaultTokenService {

    // 存放 Token 的头部名称
    @Value("${spring.security.token.header}")
    private String AUTH_HEADER;
    // Token 前缀
    @Value("${spring.security.token.prefix}")
    private String TOKEN_PREFIX;
    // 用于加密的密钥
    private String SECRET_KEY;
    // Token 的 claim 信息
    protected final String JWT_CLAIM_UUID = "UUID";
    // 单位 分钟
    private final int MINUTE = 60 * 1000;
    // 设置 Token 到期时间 秒
    private long EXPIRES;
    // Token 验证器
    private JWTVerifier verifier = null;


    @Value("${spring.security.token.expires}")
    public void setEXPIRES(int EXPIRES) {
        this.EXPIRES = (long) EXPIRES * MINUTE;
    }

    @Value("${spring.security.token.secret-key}")
    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
        this.verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
    }

    /**
     * 创建 Token
     */
    public String createToken(String uuid, TokenType type, @NotNull AuthUser user) {
        Date expireTime = new Date(System.currentTimeMillis() + EXPIRES);
        String token = JWT.create()
            .withAudience(uuid)
            .withClaim("TYPE", type.name())
            .withClaim(JWT_CLAIM_UUID, uuid)
            .withExpiresAt(expireTime)
            .sign(Algorithm.HMAC256(SECRET_KEY));
        user.setExpireTime(expireTime);
        CacheUtils.set(uuid, user, user.expired());
        return token;
    }

    public String getToken() {
        String token = ServletUtils.getRequest().getHeader(AUTH_HEADER);
        return Objects.isNull(token) ? "" : token.replace(TOKEN_PREFIX, "").trim();
    }

    /**
     * 获取请求头中的 token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        // if (Objects.isNull(token)) token = ServletUtil.getParamMap(request).get(AUTH_HEADER);
        // if (Objects.isNull(token)) token = ServletUtil.getParamMap(request).get("_token");
        return Objects.isNull(token) ? "" : token.replace(TOKEN_PREFIX, "").trim();
    }

    /**
     * 校验Token是否过期
     */
    public boolean verifyToken(String token) {
        if (StrUtil.isBlank(token)) {
            return false;
        }
        try {
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            CacheUtils.del(this.getUUID(token));
            log.error("Token已过期，请重新登录 >>> {}", e.getMessage());
            // throw new CanShowException("Token已过期，请重新登录");
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getClaim(HttpServletRequest request, String name) {
        return this.getClaim(this.getToken(request), name);
    }

    public String getClaim(String token, String name) {
        if (StrUtil.isBlank(token)) {
            log.info("token为空");
            return null;
        }
        try {
            return JWT.decode(token).getClaim(name).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public TokenType getType(String token) {
        try {
            return TokenType.valueOf(JWT.decode(token).getClaim("TYPE").asString());
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public String getUUID(HttpServletRequest request) {
        try {
            return getUUID(getToken(request));
        } catch (JWTDecodeException e) {
            return "";
        }
    }

    public String getUUID(String token) {
        try {
            return JWT.decode(token).getClaim(JWT_CLAIM_UUID).asString();
        } catch (JWTDecodeException e) {
            return "";
        }
    }

    /**
     * 从内存中获取 UserDetails 信息
     */
    public AuthUser getAuthUser() {
        return this.getAuthUser(ServletUtils.getRequest());
    }

    /**
     * 从内存中获取 UserDetails 信息
     */
    public AuthUser getAuthUser(HttpServletRequest request) {
        String token = this.getToken(request);
        // 获取token中的uuid
        if (StrUtil.isBlank(token)) {
            // throw new NullPointerException("Token为空");
            return null;
        }
        return (AuthUser) CacheUtils.get(this.getUUID(token));
    }

}
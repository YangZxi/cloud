package cn.xiaosm.cloud.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * @author Young
 * @create 2021/8/31
 * @since 1.0.0
 */
public interface AuthUser extends UserDetails, Cloneable {

    @JsonIgnore
    TokenType tokenType = null;

    default TokenType getTokenType() {
        return tokenType;
    }

    String getLoginId();

    void setExpireTime(Date expireTime);
    long expired();

    Object clone();
}
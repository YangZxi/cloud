package cn.xiaosm.cloud.common.util.cache;

import cn.hutool.crypto.digest.MD5;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
public record RedisCache(RedisTemplate<String, Object> template) implements CacheHandler {

    @PostConstruct
    public void init() {
        String key = MD5.create().toString();
        this.set(key, "0", 0);
        this.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    @Override
    public boolean set(String key, Object value, long exp) {
        if (exp == 0) template.opsForValue().set(key, value);
        else template.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public boolean put(String key, Map<String, Object> value, long exp) {
        value.forEach((k, v) -> {
            template.opsForHash().put(key, k, v);
        });
        template.expire(key, exp, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Object get(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public Object get(String key, boolean update) {
        return template.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        template.delete(key);
    }

}
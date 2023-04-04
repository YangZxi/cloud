package cn.xiaosm.cloud.core.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.common.util.SerializeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {

    @Bean("redisObject")
    public RedisTemplate<String, Object> redisObject(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setValueSerializer(new ObjectRedisSerializer());
        return template;
    }

    @Bean("redisJson")
    public RedisTemplate<String, JSON> redisJson(RedisConnectionFactory factory) {
        RedisTemplate<String, JSON> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setValueSerializer(new JsonRedisSerializer());
        return template;
    }
}

class ObjectRedisSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return SerializeUtils.serialize(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return SerializeUtils.deserialize(bytes);
    }

}

class JsonRedisSerializer implements RedisSerializer<JSON> {

    @Override
    public byte[] serialize(JSON o) throws SerializationException {
        assert o != null;
        return o.toString().getBytes();
    }

    @Override
    public JSON deserialize(byte[] bytes) throws SerializationException {
        String s = new String(bytes);
        if (s.startsWith("{")) {
            return JSONUtil.parseObj(s);
        } else if (s.startsWith("[")) {
            return JSONUtil.parseArray(s);
        } else {
            return null;
        }
    }
}
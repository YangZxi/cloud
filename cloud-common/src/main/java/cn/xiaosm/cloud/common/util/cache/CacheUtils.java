package cn.xiaosm.cloud.common.util.cache;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.xiaosm.cloud.common.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Slf4j
public class CacheUtils {

    static {
        // 判断是否使用了redis缓存
        try {
            boolean b = ClassLoaderUtil.isPresent("org.springframework.data.redis.core.RedisTemplate");
            if (b) {
                handler = (CacheHandler) SpringContextUtils.createClass("cn.xiaosm.cloud.common.util.cache.RedisCache");
                log.info("Redis 连接成功");
            } else {
                // 未使用则用java来进行存储对象
                handler = new JavaCache();
                log.warn("Redis未配置，将使用 Java 缓存");
            }
        } catch (Exception e) {
            handler = new JavaCache();
            log.warn("Redis 连接失败，将使用 Java 内存缓存数据: {}", e.getMessage());
        }
    }

    // 缓存处理器
    private static CacheHandler handler;

    public static boolean hasKey(String key) {
        return handler.hasKey(key);
    }


    public static boolean set(String key, Object value) {
        return set(key, value, 0);
    }

    /**
     * 保存对象
     * @param key
     * @param value
     * @param exp 到期时间 单位 毫秒
     */
    public static boolean set(String key, Object value, long exp) {
        return handler.set(key, value, exp);
    }

    public static boolean put(String key, Map<String, Object> value, long exp) {
        return handler.put(key, value, exp);
    }

    /**
     * 获取对象
     * @param key
     * @return
     */
    public static Object get(String key) {
        return handler.get(key);
    }

    public static Object get(String key, boolean update) {
        return handler.get(key, update);
    }

    /**
     * 删除对象
     * @param key
     */
    public static void del(String key) {
        handler.delete(key);
    }

}
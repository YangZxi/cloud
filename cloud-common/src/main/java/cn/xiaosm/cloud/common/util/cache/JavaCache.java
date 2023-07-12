package cn.xiaosm.cloud.common.util.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.ObjectUtil;

import java.util.Map;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
public class JavaCache implements CacheHandler {

    private static final long DEFAULT = DateUnit.MINUTE.getMillis() * 120;
    // 创建缓存，默认4毫秒过期
    private static final TimedCache<String, Object> cache = CacheUtil.newTimedCache(DEFAULT / 4);

    static {
        cache.schedulePrune(DateUnit.MINUTE.getMillis() * 10);
    }

    @Override
    public boolean hasKey(String key) {
        return cache.get(key) != null;
    }

    @Override
    public boolean set(String key, Object value, long exp) {
        synchronized (cache) {
            cache.put(key, value == null ? new Object() : value, exp == 0 ? DEFAULT : exp);
        }
        return true;
    }

    @Override
    public boolean put(String key, Map<String, Object> value, long exp) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Override
    public Object get(String key) {
        return ObjectUtil.clone(cache.get(key, false));
    }

    @Override
    public Object get(String key, boolean update) {
        return ObjectUtil.clone(cache.get(key, update));
    }

    @Override
    public void delete(String key) {
        cache.remove(key);
    }
}
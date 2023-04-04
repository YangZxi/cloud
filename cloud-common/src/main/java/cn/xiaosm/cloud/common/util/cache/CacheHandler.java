package cn.xiaosm.cloud.common.util.cache;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
public interface CacheHandler {

    boolean hasKey(String key);

    boolean set(@NotNull String key, @NotNull Object value, long exp);

    Object get(String key);

    Object get(String key, boolean update);

    void delete(String key);

}
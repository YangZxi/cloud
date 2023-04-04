package cn.xiaosm.cloud.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;

/**
 * @author Young
 * @create 2022/4/6
 * @since 1.0.0
 */
public class StringUtils {

    public static boolean isBlank (CharSequence str) {
        return StrUtil.isBlank(str);
    }

    public static boolean contains(Collection<String> list, String str) {
        if (isBlank(str)) return false;
        for (String s : list) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
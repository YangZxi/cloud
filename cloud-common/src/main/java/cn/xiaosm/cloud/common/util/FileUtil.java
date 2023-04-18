package cn.xiaosm.cloud.common.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Stack;

/**
 * @author Young
 * @create 2022/11/27
 * @since 1.0.0
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    public static void main(String[] args) {
        int[] matrix = new int[1];
    }

    public static String getType(MultipartFile file) {
        String type = "";
        try {
            type = FileTypeUtil.getType(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (StrUtil.isBlank(type)) {
            // 获取后缀名
            type = cn.hutool.core.io.FileUtil.extName(file.getOriginalFilename());
        }
        return type;
    }

}
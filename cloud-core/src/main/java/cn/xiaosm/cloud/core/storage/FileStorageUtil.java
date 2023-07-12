package cn.xiaosm.cloud.core.storage;

import cn.xiaosm.cloud.common.util.ServletUtils;

import java.io.File;

public class FileStorageUtil {

    public static LocalStorageService local(byte[] bytes) {
        return new LocalStorageService(bytes);
    }

    public static String download(String filename, StorageType type) {
        switch (type) {
            case LOCAL -> {
                return null;
            }
            case TENCENT -> {
                return new TencentStorageService().setScheme(ServletUtils.getScheme()).setUseCDN(true).download(filename);
            }
            case QINIU -> {
                return new QiniuStorageService().setScheme(ServletUtils.getScheme()).download(filename);
            }
            default -> {
                return null;
            }
        }
    }

    public static QiniuStorageService qiniu(File file, String filename) {
        return new QiniuStorageService(file, filename);
    }

    public static TencentStorageService tencent(File file, String filename) {
        return new TencentStorageService(file, filename);
    }
}

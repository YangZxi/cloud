package cn.xiaosm.cloud.core.storage;

import java.io.File;

public class FileStorageUtil {

    public static FileStorageService of(File file) {
        return new FileStorageService(file);
    }

    public static FileStorageService of(byte[] bytes) {
        return new FileStorageService(bytes);
    }

}

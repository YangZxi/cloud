package cn.xiaosm.cloud.core.storage;

import java.io.File;

public class FileStorageUtil {

    public static FileStorageService of(File file) {
        FileStorageService service = new FileStorageService(file);
        return service;
    }

    public static FileStorageService of(byte[] bytes) {
        FileStorageService service = new FileStorageService(bytes);
        return service;
    }

}

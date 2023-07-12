package cn.xiaosm.cloud.core.storage;

public interface FileStorageService {

    String upload();

    String download(String path);
}

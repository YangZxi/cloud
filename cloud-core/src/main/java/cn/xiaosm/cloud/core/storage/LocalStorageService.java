package cn.xiaosm.cloud.core.storage;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
@Data
@Accessors(chain = true)
public class LocalStorageService implements FileStorageService {

    private InputStream in;
    private byte[] content;
    private String path;
    private String name;
    private String filename;

    protected LocalStorageService(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("file 不是文件类型");
        }
        try (FileInputStream in = new FileInputStream(file)) {
            this.in = in;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected LocalStorageService(byte[] bytes) {
        this.content = bytes;
    }

    public String upload() {
        if (StrUtil.isBlank(path)) {
            throw new IllegalArgumentException("path 存储路径为空");
        }
        if (StrUtil.isBlank(filename)) {
            throw new IllegalArgumentException("filename 为空");
        }
        String localStorePath = UploadConfig.LOCAL_PATH;
        String relativePath = path + "/" + filename;
        File file = new File(localStorePath + "/" + relativePath);
        if (file.exists()) {
            throw new ResourceException(path + "文件已存在");
        }
        try {
            Assert.isTrue(file.createNewFile(), "文件【" + name + "】创建失败");
        } catch (IOException e) {
            throw new ResourceException("文件【" + name + "】创建失败");
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(this.content);
        } catch (IOException e) {
            log.error("文件[{}]上传失败，{}", this.filename, e.getMessage());
            throw new ResourceException("文件【" + name + "】创建失败");
        }
        log.info("文件[{}]上传成功，路径：{}", this.filename, this.path);
        return relativePath;
    }

    public String download(String path) {
        return null;
    }

    public LocalStorageService setPath(String path) {
        path = path.replaceAll("/+|\\+", "/");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        this.path = path;
        return this;
    }

}

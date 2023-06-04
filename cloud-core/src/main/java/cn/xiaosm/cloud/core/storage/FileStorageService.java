package cn.xiaosm.cloud.core.storage;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class FileStorageService {

    private InputStream in;
    private byte[] content;
    private String path;
    private String filename;

    public FileStorageService(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("file 不是文件类型");
        }
        try (FileInputStream in = new FileInputStream(file)) {
            this.in = in;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileStorageService(byte[] bytes) {
        this.content = bytes;
    }

    public void upload() {
        if (StrUtil.isBlank(path)) {
            throw new IllegalArgumentException("path 存储路径为空");
        }
        if (StrUtil.isBlank(filename)) {
            throw new IllegalArgumentException("filename 为空");
        }
        File file = new File(path + "/" + filename);
        if (file.exists()) {
            throw new ResourceException(path + "文件已存在");
        }
        try {
            Assert.isTrue(file.createNewFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(FileOutputStream out = new FileOutputStream(file)) {
            out.write(this.content);
        } catch (IOException e) {
            log.error("文件[{}]上传失败，{}", this.filename, e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("文件[{}]上传成功，路径：{}", this.filename, this.path);
    }

    public FileStorageService setPath(String path) {
        path = path.replaceAll("/+|\\+", "/");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        this.path = path;
        return this;
    }

}

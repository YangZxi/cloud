package cn.xiaosm.cloud.core.storage;

import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.Application;
import cn.xiaosm.cloud.core.config.AppConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Data
@Component
@ConfigurationProperties(
    prefix = "cloud"
)
public class UploadConfig {

    /**
     * 默认仓库（bucket）的名称
     */
    private static final String LOCAL_BUCKET = "local";
    private static final String LOCAL_CHUNK = "chunk";
    private static final String DEFAULT_LOCAL_PATH = "./%s/storage/".formatted(AppConfig.DATA_DIR);
    /**
     * 文件存储目录
     */
    public static String LOCAL_PATH;
    /**
     * 分块存储目录
     */
    public static String CHUNK_PATH;
    /**
     * 最大上传大小，单位 MB
     */
    public static Long MAX_UPLOAD_SIZE;

    private String localPath = DEFAULT_LOCAL_PATH;
    private Long maxUploadSize = 120L;

    @PostConstruct
    public void init() {
        // 设置本地存储的路径
        String path = this.localPath;
        if (StrUtil.isBlank(path)) {
            path = DEFAULT_LOCAL_PATH;
        } else if (!path.endsWith("/")) {
            path += "/";
        }
        String localPath = path + LOCAL_BUCKET;
        File localFile = getFile(localPath);
        LOCAL_PATH = localFile.getAbsolutePath();
        log.info("当前存储文件存放路径：{}", LOCAL_PATH);

        String chunkPath = path + LOCAL_CHUNK;
        File chunkFile = getFile(chunkPath);
        CHUNK_PATH = chunkFile.getAbsolutePath();
        log.info("当前分块上传缓存路径：{}", CHUNK_PATH);

        // 设置最大上传文件大小
        long mb = DataUnit.MEGABYTES.ordinal();
        if (ObjectUtils.isEmpty(maxUploadSize)) {
            MAX_UPLOAD_SIZE = mb * this.maxUploadSize;
        } else {
            try {
                MAX_UPLOAD_SIZE = maxUploadSize * mb;
                log.info("当前最大上传文件大小：{}", MAX_UPLOAD_SIZE);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("max-upload-size 格式化错误");
            }
        }
    }

    private File getFile(String path) {
        path = path.replaceAll("\\\\", "/");
        String prefix = "";
        if (path.startsWith("~/")) {
            // get a path of homedir
            prefix = System.getenv("HOME");
            if (prefix == null) {
                throw new RuntimeException("HOME 环境变量未设置");
            }
            path = path.substring(1);
        } else if (path.startsWith("./")) {
            // get a path of absolute
            prefix = new ApplicationHome(Application.class).getSource().getParent();
            path = path.substring(1);
        }
        path = prefix + path;
        File file = new File(path);
        if (!file.exists()) Assert.isTrue(file.mkdirs());
        return file;
    }

    public void setLocalPath(String path) {
        this.localPath = path;
    }

    public void setMaxUploadSize(Long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    @Data
    @Component
    @ConfigurationProperties(prefix = "cloud.qiniu")
    public static class Qiniu {
        private String accessKey;
        private String secretKey;
        private String bucket;
        private String domain;
    }

    @Data
    @Component
    @ConfigurationProperties(prefix = "cloud.tencent")
    public static class Tencent {
        private String secretId;
        private String secretKey;
        private String bucket;
        private String domain;
        private String cdnKey;
    }
}
package cn.xiaosm.cloud.core.storage;

import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.Application;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
@Data
@Component
@ConfigurationProperties(
    prefix = "cloud"
)
// @PropertySource(value = "classpath:app-config.yml", factory = YamlSourceFactory.class)
public class UploadConfig {

    /**
     * 默认仓库（bucket）的名称
     */
    private static final String LOCAL_BUCKET = "local";
    private static final String LOCAL_CHUNK = "chunk";
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

    @Component
    @ConfigurationProperties(prefix = "cloud.qiniu")
    static class Qiniu {
        static String ACCESS_KEY;
        static String SECRET_KEY;
        static String BUCKET;
        static String DOMAIN;

        public void setAccessKey(String accessKey) {
            ACCESS_KEY = accessKey;
        }
        public void setSecretKey(String secretKey) {
            SECRET_KEY = secretKey;
        }
        public void setBucket(String bucket) {
            BUCKET = bucket;
        }
        public void setDomain(String domain) {
            DOMAIN = domain;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "cloud.tencent")
    static class Tencent {
        static String SECRET_ID;
        static String SECRET_KEY;
        static String BUCKET;
        static String DOMAIN;
        static String CDN_KEY;

        public void setSecretId(String secretId) {
            SECRET_ID = secretId;
        }
        public void setSecretKey(String secretKey) {
            SECRET_KEY = secretKey;
        }
        public void setBucket(String bucket) {
            BUCKET = bucket;
        }
        public void setDomain(String domain) {
            DOMAIN = domain;
        }
        public void setCdnKey(String cdnKey) {
            CDN_KEY = cdnKey;
        }
    }

    private File getFile(String path) throws FileNotFoundException {
        path = path.replaceAll("\\\\", "/");
        String prefix = "";
        if (path.startsWith("~/")) {
            // get a path of homedir
            prefix = System.getenv("HOME");
            if (prefix == null) {
                throw new FileNotFoundException("HOME 环境变量未设置");
            }
        } else if (path.startsWith("./")) {
            // get a path of absolute
            prefix = new ApplicationHome(Application.class).getSource().getParent();
        }
        path = path.substring(1);
        path = prefix + path;
        File file = new File(path);
        if (!file.exists()) Assert.isTrue(file.mkdirs());
        return file;
    }

    public void setLocalPath(String path) throws FileNotFoundException {
        if (StrUtil.isBlank(path)) {
            path = "./storage";
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
    }

    public void setMaxUploadSize(Long arg) {
        long mb = DataUnit.MEGABYTES.ordinal();
        if (ObjectUtils.isEmpty(arg)) {
            MAX_UPLOAD_SIZE = mb * 120;
        } else {
            try {
                MAX_UPLOAD_SIZE = arg * mb;
                log.info("当前最大上传文件大小：{}", MAX_UPLOAD_SIZE);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("max-upload-size 格式化错误");
            }
        }
    }

}
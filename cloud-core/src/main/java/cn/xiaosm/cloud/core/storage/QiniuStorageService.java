package cn.xiaosm.cloud.core.storage;

import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.core.admin.entity.User;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static cn.xiaosm.cloud.core.storage.UploadConfig.Qiniu.*;

@Slf4j
@Data
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class QiniuStorageService implements FileStorageService {

    private File file;
    private String name;
    private String filename;
    private User user;
    private String scheme = "http";

    private static Configuration cfg = new Configuration(Region.huadong());

    static {
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
    }

    protected QiniuStorageService(String filename) {
        this.filename = filename;
    }

    protected QiniuStorageService(File file, String filename) {
        this.file = file;
        this.filename = filename;
    }

    public String upload() {
        UploadManager uploadManager = new UploadManager(cfg);
        String localFilePath = this.file.getAbsolutePath();

        String key = this.filename;

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET, key);
        StringMap meta = new StringMap();
        User user = this.user;
        if (user == null) {
            user = SecurityUtils.getLoginUser();
        }
        meta.put("x-qn-meta-user", user.getId());
        meta.put("x-qn-meta-name", this.getName());
        try {
            Response response = uploadManager.put(localFilePath, key, upToken, meta, null, false);
            //解析上传成功的结果
            DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
            log.info("Qiniu 上传成功, filename: {}, hash: {}", putRet.key, putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                throw new ResourceException("CDN 加速文件失败");
            }
            throw new ResourceException("CDN 加速文件失败");
        }
    }

    public String download(String path) {
        String encodedFileName = URLEncoder.encode(path, StandardCharsets.UTF_8).replace("+", "%20");
        String publicUrl = String.format("%s://%s/%s", scheme, DOMAIN, encodedFileName);

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        long expireInSeconds = 3600; // 1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        log.info("下载链接：{}", finalUrl);
        return finalUrl;
    }
}

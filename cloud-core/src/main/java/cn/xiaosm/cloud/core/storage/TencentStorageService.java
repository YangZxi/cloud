package cn.xiaosm.cloud.core.storage;

import cn.hutool.core.util.RandomUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.core.admin.entity.User;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectTagging;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.SetObjectTaggingRequest;
import com.qcloud.cos.model.Tag.Tag;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Md5Utils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.*;

@Slf4j
@Data
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class TencentStorageService implements FileStorageService {

    private File file;
    private String name;
    private String filename;
    private User user;
    private String scheme = "http";
    private boolean useCDN = false;
    private static UploadConfig.Tencent tencent;

    private static Region region = new Region("ap-shanghai");
    private static COSCredentials cred;
    static {
        cred = new BasicCOSCredentials(tencent.getSecretId(), tencent.getSecretKey());
    }

    protected TencentStorageService(File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new ResourceException("文件不存在");
        }
        this.file = file;
        this.filename = UUID.randomUUID().toString();
    }

    protected TencentStorageService(File file, String filename) {
        if (!file.exists() || file.isDirectory()) {
            throw new ResourceException("文件不存在");
        }
        this.file = file;
        this.filename = filename;
    }

    public void delete(String path) {
        // delete file
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setConnectionTimeout(5000);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        cosClient.deleteObject(tencent.getBucket(), path);
    }

    public String upload() {
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setConnectionTimeout(5000);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传的文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(tencent.getBucket(), filename, file);

        long l = System.currentTimeMillis();
        log.info("Tencent 上传文件, filename: {}", filename);
        try {
            PutObjectResult result = cosClient.putObject(putObjectRequest);
            log.info("Tencent 上传成功, duration: {} ms, filename: {}, hash: {}",
                    System.currentTimeMillis() - l, filename, result.getContentMd5());

            // 设置对象标签
            List<Tag> tags = new ArrayList<>();
            tags.add(new Tag("user", user.getId().toString()));
            tags.add(new Tag("name", name));
            SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(tencent.getBucket(), filename, new ObjectTagging(tags));
            cosClient.setObjectTagging(setObjectTaggingRequest);

            return filename;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceException("CDN 加速文件失败");
        } finally {
            cosClient.shutdown();
        }
    }

    public String download(String path) {
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.valueOf(scheme));
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
        // 详细代码参见本页：简单操作 -> 创建 COSClient
        COSClient cosClient = new COSClient(cred, clientConfig);
        String newUrl;
        if (useCDN) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            String domain = tencent.getDomain();
            long timestamp = System.currentTimeMillis() / 1000;
            String rand = RandomUtil.randomString(10);
            // 文件路径-timestamp-rand-uid-自定义密钥 的MD5值
            String md5Hex = Md5Utils.md5Hex(path + "-" + timestamp + "-" + rand + "-" + "0" + "-" + tencent.getCdnKey());
            // http://DomainName/Filename?sign=timestamp-rand-uid-md5hash
            // domain + path + "?sign=" + timestamp + "-" + rand + "-" + "0" + "-" + md5Hex
            newUrl = String.format("//%s%s?sign=%s-%s-%s-%s", domain, path, timestamp, rand, "0", md5Hex);
        } else {
            // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
            // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
            // 这里设置签名在半个小时后过期
            Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
            // 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
            Map<String, String> params = new HashMap<>();
            // 填写本次请求的头部，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的头部
            Map<String, String> headers = new HashMap<>();
            // params.put("Host", "https://tencent.cdn.xiaosm.cn");
            // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
            URL url = cosClient.generatePresignedUrl(tencent.getBucket(), path, expirationDate, HttpMethodName.GET, headers, params);
            newUrl = url.toString();
            // newUrl = newUrl.replace(url.getHost(), DOMAIN);
            // 确认本进程不再使用 cosClient 实例之后，关闭即可
        }
        cosClient.shutdown();
        log.info("Tencent 获取下载链接成功, url: {}", newUrl);
        return newUrl;
    }
}

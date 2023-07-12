package cn.xiaosm.cloud.core.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.core.service.ShareService;
import cn.xiaosm.cloud.core.service.impl.ResourceService;
import cn.xiaosm.cloud.core.util.download.DownloadUtil;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.entity.TokenType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 本类中的方法，均需要获取 unsafe_token 后才可以访问
 *
 * @author Young
 * @create 2022/9/9
 * @since 1.0.0
 */
@Log4j2
@RestController
@RequestMapping
public class PreviewController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    ShareService shareService;
    @Autowired
    TokenService tokenService;

    /**
     * 创建用于文件预览的链接
     */
    @PostMapping("api/resource/link/{id}")
    @PreAuthorize("hasAuthority('resource:preview')")
    public RespBody buildLink(
            @PathVariable("id") String id
    ) {
        AuthUser authUser = ((AuthUser) SecurityUtils.getAuthentication().getPrincipal());
        TokenType type = authUser.getTokenType();
        ResourceDTO resourceDTO = new ResourceDTO();
        switch (type) {
            case LOGIN -> {
                resourceDTO.setId(Long.valueOf(id));
                resourceDTO.setUserId(SecurityUtils.getLoginUserId());
                String url = resourceService.buildLink(resourceDTO);
                return RespUtils.success("OK", url);
            }
            case SHARE -> {
                Resource res = (Resource) CacheUtils.get("S" + id);
                if (res != null) {
                    BeanUtils.copyProperties(res, resourceDTO);
                    File file = resourceService.getLocalFile(res);
                    if (!file.exists()) {
                        return RespUtils.fail("资源不存在");
                    }
                    resourceDTO.setFileAbPath(file.getAbsolutePath());
                }

                String uuid = IdUtil.fastSimpleUUID();
                String url = resourceDTO.getUrl();
                CacheUtils.set(uuid, resourceDTO, authUser.expired() - System.currentTimeMillis());
                return RespUtils.success("OK", url);
            }
        }
        return RespUtils.fail("获取预览链接失败");
    }

    /**
     * 文件下载请求
     */
    @GetMapping("download/{uuid}")
    public Object download(
            @PathVariable("uuid") String uuid,
            @RequestParam("token") String token,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String key = getCacheKey(token, uuid);
        ResourceDTO resource = (ResourceDTO) CacheUtils.get(key, true);
        if (resource == null) {
            log.info("资源已过期");
            return RespUtils.fail("资源已过期");
        }
        File file = new File(resource.getFileAbPath());
        if (!file.exists()) {
            CacheUtils.del(key);
            log.info("资源已被删除");
            return RespUtils.fail("资源已被删除");
        }
        response.reset();
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            //unknown mimetype so set the mimetype to application/octet-stream
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        // 分段下载，响应头
        response.setHeader("Accept-Ranges", "bytes");
        // 设置文件名
        response.setHeader("Content-Disposition",
            "attachment;fileName=\"" + URLUtil.encode(resource.getName(), Charset.defaultCharset()) + "\"");
        DownloadUtil.outputData(request, response, file);
        return null;
    }

    @GetMapping("preview/{uuid}")
    @PreAuthorize("hasAuthority('resource:preview')")
    public RespBody preview(
        @PathVariable("uuid") String uuid,
        @RequestParam("token") String token,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        ResourceDTO resourceDTO = (ResourceDTO) CacheUtils.get(getCacheKey(token, uuid), true);
        return previewHandler(resourceDTO, request, response);
    }

    private final Long MAX_SIZE = (1 << 20) * 10L; // 10MB

    public String getCacheKey(String token, String uuid) {
        return String.format("%s:%s", token, uuid);
    }

    public RespBody previewHandler(ResourceDTO resourceDTO, HttpServletRequest request, HttpServletResponse response) {
        if (resourceDTO == null) {
            return RespUtils.fail("资源不存在");
        }
        File file;
        if (StrUtil.isBlank(resourceDTO.getFileAbPath())
            || !(file = new File(resourceDTO.getFileAbPath())).exists())
            return RespUtils.fail("文件不存在");
        // if (resourceDTO.getSize() > MAX_SIZE) return RespUtils.fail("文件过大，暂不支持在线预览");
        String contentType = getContentType(resourceDTO.getType());
        if (StrUtil.isBlank(contentType)) return RespUtils.fail("当前文件类型暂不支持预览");
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Access-Control-Allow-Origin", "*");

        DownloadUtil.outputData(request, response, file);
        return null;
    }

    private static final Map<String, String> RESOURCE_TYPE = new HashMap<>(){{
       put("plain", "text/plain;charset=UTF-8");
       put("jpg", "image/jpeg");
       put("jpeg", "image/jpeg");
       put("gif", "image/gif");
       put("png", "image/png");
       put("xml", "text/xml");
       put("mp4", "video/mp4");
       put("mov", "video/quicktime");
       put("mp3", "audio/mpeg");
       put("ogg", "audio/ogg");
       put("pdf", "application/pdf");
       put("docx", "application/octet-stream");
       put("doc", "application/octet-stream");
       put("xls", "application/octet-stream");
       put("xlsx", "application/octet-stream");
    }};

    private static final String[] TEXT_TYPE = new String[]{
        "txt", "html", "css", "java", "ts", "js", "py", "c", "cpp", "md", "sql"
    };

    private String getContentType(String type) {
        if (ArrayUtil.contains(TEXT_TYPE, type)) {
            return RESOURCE_TYPE.get("plain");
        } else {
            return RESOURCE_TYPE.get(type);
        }
    }

}
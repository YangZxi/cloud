package cn.xiaosm.cloud.core.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.exception.ShareException;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.Share;
import cn.xiaosm.cloud.core.entity.request.ShareVO;
import cn.xiaosm.cloud.core.entity.response.ShareDTO;
import cn.xiaosm.cloud.core.service.ShareService;
import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import cn.xiaosm.cloud.security.entity.ShareUser;
import cn.xiaosm.cloud.security.entity.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Slf4j
@Api("share")
public class ShareController {

    @Autowired
    ShareService shareService;
    @Autowired
    TokenService tokenService;
    @Autowired
    PreviewController previewController;

    private static final String SHARE_LIST_CACHE = "share:";

    @RequestMapping("create")
    @LogRecord("资源分享")
    public RespBody create(@RequestBody @Validated ShareDTO shareDTO) {
        ShareDTO dto = shareService.create(shareDTO);
        ShareVO vo = new ShareVO(dto);
        return RespUtils.success(vo);
    }

    @RequestMapping("list")
    @PreAuthorize("hasRole('ROLE_share')")
    public RespBody preview(@RequestBody ShareDTO share) {
        Assert.isTrue(hasShare(share.getId()), () -> new ShareException("当前分享的资源在地球找不到啦！"));
        ShareDTO dto = shareService.info(share);
        if (dto.getResourceList().size() == 1) {
            // CacheUtils.set(dto.getResourceList().get(0).getId(),);
            Resource resource = dto.getResourceList().get(0);
            CacheUtils.set("S" + resource.getId(), resource);
        }
        return RespUtils.success(new ShareVO(dto));
    }

    /**
     * 获取需要访问分享资源的 Token
     */
    @RequestMapping("pass")
    @AnonymousAccess
    public ResponseEntity<RespBody> pass(@RequestBody ShareDTO dto, HttpServletRequest request, HttpServletResponse response) {
        String token = tokenService.getToken(request);
        // token 为空，验证密码
        if (StrUtil.isBlank(token)) {
            Share share = shareService.checkPass(dto);
            RespUtils.sendToken(response, tokenService.createShareToken(share.getId()));
            return null;
        }
        // token 不为空，验证token
        if (!tokenService.verifyToken(token)) {
            return new ResponseEntity<>(RespUtils.build(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
        }
        if (StrUtil.isBlank(dto.getId())) return new ResponseEntity<>(RespUtils.fail("当前分享的资源在地球找不到啦!"), HttpStatus.OK);
        TokenType type = tokenService.getType(request);
        if (!TokenType.SHARE.equals(type)) {
            log.error("不是 Share token, token={}, type={}", token, type);
            return new ResponseEntity<>(RespUtils.fail("error"), HttpStatus.OK);
        }
        // 验证 token 中的参数是否和当前分享 id 相同
        if (dto.getId().equals(tokenService.getClaim(token, "shareId"))) {
            RespUtils.sendToken(response, token);
            return null;
        }
        return new ResponseEntity<>(RespUtils.fail("当前分享资源暂不可访问"), HttpStatus.OK);
    }

    /**
     * 分享中的下载不同于登录用户的下载
     * 需要同时提供文件id 和当前访问的 path 路径，以便进行所属权判定
     */
    @PostMapping("link")
    @PreAuthorize("hasRole('ROLE_share')")
    public RespBody buildLink(@RequestBody ShareDTO shareDTO) {
        if (null == shareDTO.getResourceId()) return RespUtils.fail("资源ID不可以为空");
        shareDTO.setId(((ShareUser) SecurityUtils.getAuthentication().getPrincipal()).getShareId());
        String url = shareService.buildLink(shareDTO);
        return RespUtils.success("OK", url);
    }

    @RequestMapping("/short_url")
    public RespBody shortUrl() {
        //
        ShareDTO shareDTO = new ShareDTO();
        shareDTO.setId(((ShareUser) SecurityUtils.getAuthentication().getPrincipal()).getShareId());
        ShareDTO data = shareService.shortUrl(shareDTO);
        return RespUtils.success(data.getShortCode());
    }

    private boolean hasShare(String shareId) {
        if (StrUtil.isBlank(shareId)) return false;
        ShareUser principal = (ShareUser) SecurityUtils.getAuthentication().getPrincipal();
        // 不为空，且 id 相等
        return null != principal && shareId.equals(principal.getShareId());
    }
}
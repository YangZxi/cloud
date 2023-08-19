package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.request.ShareVO;
import cn.xiaosm.cloud.core.entity.response.ShareDTO;
import cn.xiaosm.cloud.core.service.ShareService;
import cn.xiaosm.cloud.security.entity.ShareUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public RespBody list() {
        return RespUtils.success(shareService.listByUserId(SecurityUtils.getLoginUserId()));
    }

    @RequestMapping("update")
    @LogRecord("更新分享")
    public RespBody update(@RequestBody ShareDTO shareDTO) {
        if (shareService.update(shareDTO)) {
            return RespUtils.success("更新成功");
        }
        return RespUtils.fail("更新失败");
    }

    @RequestMapping("delete/{id}")
    @LogRecord("删除分享")
    public RespBody delete(@PathVariable("id") Integer id) {
        if (shareService.removeById(id)) {
            return RespUtils.success("删除成功");
        }
        return RespUtils. fail("删除失败");
    }

    @RequestMapping("/short_url")
    public RespBody shortUrl() {
        //
        ShareDTO shareDTO = new ShareDTO();
        shareDTO.setId(((ShareUser) SecurityUtils.getAuthentication().getPrincipal()).getShareId());
        ShareDTO data = shareService.shortUrl(shareDTO);
        return RespUtils.success(data.getShortCode());
    }

}
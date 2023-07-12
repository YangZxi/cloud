package cn.xiaosm.cloud.core.service;

import cn.xiaosm.cloud.core.admin.service.BaseService;
import cn.xiaosm.cloud.core.entity.Share;
import cn.xiaosm.cloud.core.entity.dto.ShareDTO;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
public interface ShareService extends BaseService<Share> {

    ShareDTO create(ShareDTO dto);

    Share checkPass(ShareDTO dto);

    ShareDTO info(ShareDTO share);

    String buildLink(ShareDTO shareDTO);

    ShareDTO shortUrl(ShareDTO shareDTO);
}
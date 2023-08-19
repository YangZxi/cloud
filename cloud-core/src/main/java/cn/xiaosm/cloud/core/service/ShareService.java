package cn.xiaosm.cloud.core.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.exception.ShareException;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.Share;
import cn.xiaosm.cloud.core.entity.response.ShareDTO;
import cn.xiaosm.cloud.core.entity.response.ShareListDTO;
import cn.xiaosm.cloud.core.mapper.ResourceMapper;
import cn.xiaosm.cloud.core.mapper.ShareMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShareService extends ServiceImpl<ShareMapper, Share> {

    @Autowired
    ResourceService resourceService;
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ShareMapper shareMapper;
    @Autowired
    TokenService tokenService;

    public ShareDTO create(ShareDTO dto) {
        // 根据 id 查询当前登录用户的资源
        List<Resource> resourceList = resourceService.getByCurrentUser(dto.getResourceIds());
        Assert.notEmpty(resourceList, () -> new CanShowException("资源不存在"));
        Share share = new Share();
        share.setId(IdUtil.simpleUUID());
        share.setDeadline(dto.getDeadline());
        share.setUserId(SecurityUtils.getLoginUserId());
        share.setResourceIds(dto.getResourceIds());
        // 如果密码不为 null，将设置密码访问
        if (null != dto.getPassword()) {
            if (StrUtil.isBlank(dto.getPassword())) {
                // 随机生成四位密码
                share.setPassword(RandomUtil.randomString(4));
            } else {
                // 使用自定义密码
                share.setPassword(dto.getPassword());
            }
        }
        int i = shareMapper.insert(share);
        Assert.isTrue(i == 1,() -> new ShareException("资源分享失败"));
        BeanUtils.copyProperties(share, dto);
        return dto;
    }

    public List<ShareListDTO> listByUserId(Long userId) {
        List<Share> shareList = shareMapper.listByUserId(userId);
        List<ShareListDTO> shareListDTOList = new ArrayList<>();
        // 获取每个链接里的资源名称
        shareList.forEach(share -> {
            List<Resource> resourceList = resourceService.listByIds(share.getResourceIds());
            String name = resourceList.stream().map(Resource::getName).reduce((a, b) -> a + ", " + b).orElse("");
            ShareListDTO dto = new ShareListDTO(share);
            dto.setShareLabel(name);
            shareListDTOList.add(dto);
        });
        return shareListDTOList;
    }

    public boolean update(Share share) {
        Share db = shareMapper.selectById(share.getAutoId());
        if (null == db || !SecurityUtils.getLoginUserId().equals(db.getUserId())) {
            throw new ShareException("当前分享的资源在地球找不到啦！");
        }
        Optional.ofNullable(share.getDeadline()).ifPresent(db::setDeadline);
        Optional.ofNullable(share.getPassword()).ifPresent(db::setPassword);
        return shareMapper.updateById(db) == 1;
    }

    public boolean delete(Integer id) {
        Share db = shareMapper.selectById(id);
        if (null == db || !SecurityUtils.getLoginUserId().equals(db.getUserId())) {
            throw new ShareException("当前分享的资源在地球找不到啦！");
        }
        return shareMapper.deleteById(id) == 1;
    }

    public ShareDTO shortUrl(ShareDTO shareDTO) {
        Share share = shareMapper.selectById(shareDTO.getAutoId());
        if (StrUtil.isBlank(share.getShortCode())) {

        }
        shareDTO.setShortCode(share.getShortCode());
        return shareDTO;
    }

}
package cn.xiaosm.cloud.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.common.exception.ShareException;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.Share;
import cn.xiaosm.cloud.core.entity.response.ResourceDTO;
import cn.xiaosm.cloud.core.entity.response.ShareDTO;
import cn.xiaosm.cloud.core.mapper.ResourceMapper;
import cn.xiaosm.cloud.core.storage.FileStorageUtil;
import cn.xiaosm.cloud.core.storage.StorageType;
import cn.xiaosm.cloud.security.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicService {

    private final ShareService shareService;
    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;
    private final TokenService tokenService;

    public ShareDTO info(ShareDTO shareDTO) {
        Share db = this.getByIdAndDeadlineWithCheckLogin(shareDTO);
        // 根据 ids 获取资源信息
        // 获取当前分享下所有文件和一级目录
        List<Resource> resourceList = resourceService.listByIds(db.getResourceIds());
        if (StrUtil.isBlank(shareDTO.getPath()) || "/".equals(shareDTO.getPath())) {

        } else {
            Long parentId = this.getResourceIdByPath(shareDTO.getPath(), resourceList);
            resourceList = resourceMapper.listByParentId(parentId);
        }
        ShareDTO dto = new ShareDTO();
        BeanUtil.copyProperties(db, dto);
        dto.setResourceList(resourceList);
        return dto;
    }

    /**
     * 根据全路径获取资源的 id
     * @param resourceList 根路径下所有文件
     */
    private Long getResourceIdByPath(String fullPath, List<Resource> resourceList) {
        // 暂时先使用java循环来找进入文件夹叭
        if (fullPath.startsWith("/")) fullPath = fullPath.substring(1);
        String[] path = fullPath.split("/");
        // 获取到基准目录的 id
        Long parentId = null;
        Integer bucketId = null;
        for (Resource resource : resourceList) {
            if (path[0].equals(resource.getName())) {
                parentId = resource.getId();
                bucketId = resource.getBucketId();
                break;
            }
        }
        if (null == parentId) {
            throw new ResourceException(path[0] + "-资源不存在");
        }
        for (int i = 1; i < path.length; i++) {
            if ("".equals(path[i])) continue;
            parentId = resourceMapper.selectIdByBucketAndNameAndDir(bucketId, parentId, path[i]);
            if (null == parentId) throw new ResourceException(path[i] + "-资源不存在");
        }
        return parentId;
    }


    public String createDownloadLink(ShareDTO shareDTO) {
        // 获取所有分享资源时判断是否过期
        Share db = this.getByIdAndDeadlineWithCheckLogin(shareDTO);
        Resource resource;
        if (StrUtil.isBlank(shareDTO.getPath())) {
            // 如果不包含此资源
            Assert.isFalse(ArrayUtil.contains(db.getResourceIds().split(","), shareDTO.getId()),
                    () -> new ShareException("当前分享的资源在地球找不到啦！"));
            resource = resourceMapper.selectById(shareDTO.getResourceId());
        } else {
            // 文件夹内的资源
            log.error("暂不支持的操作，文件夹内的资源");
            throw new ShareException("暂不支持的操作");
        }
        if (null == resource) {
            throw new ShareException("当前分享的资源在地球找不到啦！");
        }
        ResourceDTO resourceDTO = new ResourceDTO();
        if (StrUtil.isNotBlank(resource.getCdn())) {
            String url = FileStorageUtil.download(resource.getCdn(), StorageType.TENCENT);
            return url;
        } else {
            File file = resourceService.getLocalFile(resource);
            if (!file.exists()) throw new ResourceException("当前资源在地球找不到啦！1001");
            resourceDTO.setName(resource.getName());
            resourceDTO.setType(resource.getType());
            resourceDTO.setSize(resource.getSize());
            resourceDTO.setFileAbPath(file.getAbsolutePath());
            String uuid = tokenService.getUUID(tokenService.getToken());
            String url = "/" + resource.getId() + "?token=" + uuid;
            AuthUser authUser = ((AuthUser) SecurityUtils.getAuthentication().getPrincipal());
            CacheUtils.set(uuid + ":" + resource.getId(), resourceDTO, authUser.expired());
            return url;
        }
    }

    @NotNull
    public Share checkPass(ShareDTO dto) {
        Share db = this.getByIdAndDeadlineWithCheckLogin(dto);
        // 分享有密码，但是未提供密码，或密码不正确
        if (StrUtil.isNotBlank(db.getPassword()) && !db.getPassword().equals(dto.getPassword())) {
            throw new ShareException("密码错误");
        }
        return db;
    }

    @NotNull
    public Share getByIdAndDeadlineWithCheckLogin(ShareDTO dto) {
        // 获取当前分享
        Share db = shareService.getById(dto.getId());
        if (SecurityUtils.getUser() != null) {
            if (!SecurityUtils.getUser().getLoginId().equals(db.getId())) {
                throw new ShareException("当前分享的资源在地球找不到啦！");
            }
        }
        // 不是永久资源，且资源已过期
        if (null != db.getDeadline() && LocalDateTime.now().isAfter(db.getDeadline())) {
            throw new ShareException("当前分享链接已过期！");
        }
        return db;
    }
}

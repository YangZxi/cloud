package cn.xiaosm.cloud.core.service;

import cn.xiaosm.cloud.core.admin.service.BaseService;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.core.entity.dto.UploadDTO;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public interface ResourceService extends BaseService<Resource> {

    Resource getByCurrentUser(Long id);

    List<Resource> getByCurrentUser(String ids);

    List<Resource> listByIds(String ids);

    List<Resource> list(ResourceDTO resource);

    boolean saveContent(ResourceDTO dto);

    boolean rename(ResourceDTO resource);

    boolean move(Long originId, Long targetId);

    boolean delete(Resource resource);

    File getLocalFile(Resource resource);

    String upload(UploadDTO uploadDTO);

    Boolean merge(UploadDTO uploadDTO);

    boolean existCurrentPath(UploadDTO dto);

    ResourceDTO download(ResourceDTO resource);

    String create(ResourceDTO resource);

    ResourceDTO preview(ResourceDTO resourceDTO);

    @Transactional
    boolean copy(Long originId, Long targetId);

    boolean copy(List<Resource> children, Resource target);

    ResourceDTO offlineDownload(String url);

    List<Resource> search(ResourceDTO dto);
}
package cn.xiaosm.cloud.core.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.core.entity.dto.UploadDTO;
import cn.xiaosm.cloud.core.entity.vo.ResourceVO;
import cn.xiaosm.cloud.core.service.ChunkService;
import cn.xiaosm.cloud.core.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Api("resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    ChunkService chunkService;

    @RequestMapping("search")
    public RespBody search(@RequestBody ResourceDTO dto) {
        if (StrUtil.isBlank(dto.getName())) return RespUtils.fail("文件名不可以为空");
        if (StrUtil.isBlank(dto.getBucketName())) return RespUtils.fail("仓库名不可以为空");
        return RespUtils.success(resourceService.search(dto));
    }

    /**
     * 获取文件信息
     * @param resource
     * @return
     */
    @GetMapping
    public RespBody listResource(ResourceDTO resource) {
        if (StrUtil.isBlank(resource.getBucketName())) return RespUtils.success("仓库名称不可以为空");
        List<Resource> list = resourceService.list(resource);
        return RespUtils.success(list);
    }

    /**
     * 文件、目录创建
     * @param resource
     * @return
     */
    @PostMapping("create")
    public RespBody create(@RequestBody ResourceDTO resource) {
        if (StrUtil.isBlank(resource.getBucketName())) return RespUtils.fail("仓库名称不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("文件名不可以为空");
        return RespUtils.success( "文件【" + resource.getName() + "】创建成功", resourceService.create(resource));
    }

    /**
     * 文件重命名
     * @param resource
     * @return
     */
    @PostMapping("rename")
    public RespBody rename(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("资源id不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("资源文件名不可以为空");
        return RespUtils.success(resourceService.rename(resource));
    }

    @PostMapping("copy")
    public RespBody copy(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("源资源id不可以为空");
        if (null == resource.getTargetId()) return RespUtils.fail("目标资源id不可以为空");
        return RespUtils.success(resourceService.copy(resource.getId(), resource.getTargetId()) ? "复制成功" : "复制失败");
    }

    @PostMapping("move")
    public RespBody move(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("源资源id不可以为空");
        if (null == resource.getTargetId()) return RespUtils.fail("目标资源id不可以为空");
        return RespUtils.success(resourceService.move(resource.getId(), resource.getTargetId()) ? "移动成功" : "移动失败");
    }

    /**
     * 文件重命名
     * @param resource
     * @return
     */
    @PostMapping("saveContent")
    public RespBody saveContent(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("资源id不可以为空");
        if (resource.getContent() == null) {
            resource.setContent("");
        }
        return RespUtils.success(resourceService.saveContent(resource) ? "保存成功" : "保存失败");
    }

    /**
     * 资源删除
     * @param resource
     * @return
     */
    @RequestMapping("delete")
    public RespBody delete(@RequestBody ResourceVO resource) {
        ResourceDTO dto = new ResourceDTO();
        dto.setId(resource.getId());
        return resourceService.delete(dto) ? RespUtils.success() : RespUtils.fail();
    }

    /**
     * 上传前检查
     * @param dto
     * chunkNumber=1
     * chunkSize=31457280
     * currentChunkSize=43247
     * totalSize=43247
     * identifier=edfd8c2fd41e8ac5af60cce486f955c3
     * filename=avatar.jpg
     * relativePath=avatar.jpg
     * totalChunks=1
     * bucketName=local
     * path=
     * @return
     */
    @GetMapping("upload")
    public RespBody uploadCheck(UploadDTO dto) {
        System.out.println(dto.toString());
        Map<String, Object> data = new HashMap<>();
        Resource r = resourceService.checkByHashOrNameInPath(dto);
        if (r != null) {
            data.put("uploaded", true);
            resourceService.quickUpload(r.getId(), dto);
            return RespUtils.success("文件上传成功", data);
        } else {
            data.put("uploaded",  false);
            data.put("uploadedChunks", chunkService.getUploaded(dto.getIdentifier()));
            return RespUtils.success("文件有分块未上传", data);
        }
    }

    @PostMapping("upload")
    @LogRecord("文件上传")
    public RespBody upload(UploadDTO uploadDTO) {
        if (uploadDTO.getFile() == null
            || uploadDTO.getFile().isEmpty()
            || StrUtil.isBlank(uploadDTO.getIdentifier())) return RespUtils.fail("文件不可以为空");
        resourceService.upload(uploadDTO);
        return RespUtils.success("文件上传成功");
    }

    @PostMapping("upload/merge")
    @LogRecord("文件上传-合并操作")
    public RespBody uploadMerge(@RequestBody UploadDTO dto) {
        return RespUtils.success(resourceService.merge(dto) ? "文件上传成功" : "文件上传失败");
    }

    /**
     * 文件预下载处理
     * @param resource
     * @return
     */
    @PostMapping("pre_download")
    // @PreAuthorize("hasRole('ROLE_normal')")
    public RespBody makeDownload(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("文件ID不可以为空");
        resource = resourceService.download(resource);
        String uuid = IdUtil.simpleUUID();
        // 3 分钟后过期
        CacheUtils.set(uuid, resource, DateUnit.MINUTE.getMillis() * 3);
        return RespUtils.success("OK", uuid);
    }

}
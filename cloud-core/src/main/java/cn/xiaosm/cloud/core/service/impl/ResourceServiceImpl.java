package cn.xiaosm.cloud.core.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.common.util.FileUtil;
import cn.xiaosm.cloud.core.config.EditableType;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.entity.Bucket;
import cn.xiaosm.cloud.core.entity.Resource;
import cn.xiaosm.cloud.core.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.core.entity.dto.ResourceParentDTO;
import cn.xiaosm.cloud.core.entity.dto.UploadDTO;
import cn.xiaosm.cloud.core.mapper.ResourceMapper;
import cn.xiaosm.cloud.core.service.ChunkService;
import cn.xiaosm.cloud.core.service.ResourceService;
import cn.xiaosm.cloud.core.storage.FileStorageUtil;
import cn.xiaosm.cloud.core.storage.UploadConfig;
import cn.xiaosm.cloud.core.util.download.DlTaskInfo;
import cn.xiaosm.cloud.core.util.download.DownloadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Slf4j
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    /**
     * 文件名不可用字符
     */
    public final static String ILLEGAL_CHAR = "\\/:*\"<>|";
    private final static Long ROOT_ID = 0L;

    @Autowired
    LocalBucketServiceImpl bucketService;
    @Autowired
    ChunkService chunkService;
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    DownloadService downloadService;

    /**
     * 通过 id 获取当前登录用户的资源
     *
     * @param id
     * @return
     */
    @Override
    public Resource getByCurrentUser(Long id) {
        return resourceMapper.selectByIdAndUser(id, SecurityUtils.getLoginUserId());
    }

    @Override
    public List<Resource> getByCurrentUser(String ids) {
        return resourceMapper.selectByIdsAndUser(ids, SecurityUtils.getLoginUserId());
    }

    @Override
    public List<Resource> listByIds(String ids) {
        return resourceMapper.selectByIdsAndUser(ids, null);
    }

    @Override
    public List<Resource> list(ResourceDTO resource) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        // 后续的接口不需要再使用 userId 来查询，因为在上面的仓库查询中使用过 userId 筛选过了
        List<Resource> resources = new ArrayList<>();
        if (resource.getParentId() != null && resource.getParentId() > 0) { // 如果有父级 id，则根据父级 id 查询
            QueryWrapper<Resource> wrapper = new QueryWrapper();
            wrapper.select("id").eq("id", resource.getParentId())
                .eq("user_id", SecurityUtils.getLoginUserId());
            Resource db = resourceMapper.selectOne(wrapper);
            // 判断当前用户是否拥有 parentId 的资源
            Assert.notNull(db, "目录不存在");
            resources = resourceMapper.listByParentId(resource.getParentId());
        } else if (StrUtil.isBlank(resource.getPath()) || "/".equals(resource.getPath())) { // 检索根目录文件
            // 获取当前仓库根目录下所有文件
            resources = resourceMapper.listRoot(0, bucket.getId());
        } else if (StrUtil.isNotBlank(resource.getPath())) { // 检索指定目录下的文件
            Long parentId = getIdByPath(bucket.getId(), resource.getPath());
            resources = resourceMapper.listByParentId(parentId);
        }
        // 根据类型过滤
        if (StrUtil.isNotBlank(resource.getType())) {
            resources = resources.stream().filter(el -> resource.getType().equals(el.getType())).collect(Collectors.toList());
        }
        resources.sort((el1, el2) -> {
            // 如果文件同类型，则按照文件首字母排序
            if (el1.isDir() == el2.isDir()) return el1.getName().compareToIgnoreCase(el2.getName());
            // 文件夹在前，文件在后
            return el1.isDir() ? -1 : 1;
        });
        return resources;
    }

    private Long getIdByPath(Integer bucketId, String fullPath) {
        if (fullPath.length() == 0 || "/".equals(fullPath)) return ROOT_ID;
        // 暂时先使用java循环来找进入文件夹叭
        String[] dirs = fullPath.split("/");
        Long parentId = ROOT_ID;
        for (String dir : dirs) {
            if ("".equals(dir)) continue;
            parentId = resourceMapper.selectIdByBucketAndNameAndDir(bucketId, parentId, dir);
            if (null == parentId) throw new ResourceException(dir + "-目录不存在");
        }
        return parentId;
    }

    @Override
    public Long create(ResourceDTO resource) {
        // 校验文件名
        if (!checkName(resource.getName())) throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        // 获取父级菜单
        Long parentId = getIdByPath(bucket.getId(), resource.getPath());
        if (null == parentId) throw new ResourceException(resource.getPath() + "目录不存在");
        // 校验名字是否重复
        Resource exist = resourceMapper.selectOne(new QueryWrapper<Resource>().eq("parent_id", parentId).eq("name", resource.getName()).select("id"));
        // 当文件名重复时
        if (!(null == exist || null == exist.getId())) {
            throw new ResourceException("文件名重复");
        }
        Resource db = new Resource(bucket);
        db.setName(resource.getName());
        db.setParentId(parentId);
        // 处理文件或目录
        String path = suffixPath();
        String fileName = null;
        if (resource.isDir()) {
            db.setType("dir");
        } else {
            String uuid = IdUtil.simpleUUID();
            // 本地文件名格式：uuid.[fileType]
            String fileType = FileUtil.extName(resource.getName());
            if (StrUtil.isBlank(fileType)) {
                fileName = uuid;
                db.setType("txt");
            } else {
                fileName = uuid + "." + fileType;
                db.setType(fileType);
            }
            db.setPath(path + "/" + fileName);
            db.setSize(0l);
            // 因为刚开始创建的是空文件，所以不计算hash，使用 uuid
            db.setHash(uuid);
        }
        try {
            // 数据库中创建数据后创建文件
            if (resourceMapper.insert(db) == 1) {
                // 创建文件
                if (!db.isDir()) {
                    FileStorageUtil.of(new byte[1]).setPath(path).setFilename(fileName).upload();
                }
                log.info("文件创建成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件创建失败");
            throw new ResourceException("文件【" + resource.getName() + "】创建失败");
        }
        return db.getId();
    }

    @Override
    public boolean saveContent(ResourceDTO dto) {
        // 获取数据库中的数据
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("id", dto.getId()).eq("user_id", SecurityUtils.getLoginUserId());
        Resource db = resourceMapper.selectOne(wrapper);
        // 如果不是属于自己的资源
        Assert.notNull(db, () -> new ResourceException("资源不存在"));
        // 判断文件是否属于可编辑类型
        Assert.isTrue(EditableType.isEditable(db.getType()), () -> new ResourceException("文件不可编辑"));
        // 获取File文件
        File file = this.getLocalFile(db);
        Assert.isTrue(file.exists() && file.isFile(), () -> new ResourceException("资源不存在或已被删除"));
        FileUtil.writeBytes(dto.getContent().getBytes(), file);
        // 更新数据库信息
        Resource save = new Resource();
        BeanUtils.copyProperties(db, save);
        save.setId(dto.getId());
        save.setSize(file.length());
        // 计算hash
        // save.setHash(DigestUtil.md5Hex(dto.getContent().getBytes()));
        save.setUpdateTime(LocalDateTime.now());
        resourceMapper.updateById(save);
        return true;
    }

    @Override
    public boolean rename(ResourceDTO resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        String fileName = resource.getName();
        // 文件名相同，跳过修改
        if (fileName.equals(db.getName())) return true;
        // 校验名字是否重复
        if (!checkNameAndUnique(fileName, db.getParentId(), db.getBucketId()))
            throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
        Resource update = new Resource();
        update.setId(db.getId());
        update.setName(fileName);
        return resourceMapper.updateById(update) == 1;
    }

    /**
     * 复制文件
     *
     * @param originId
     * @param targetId
     * @return
     */
    @Override
    @Transactional
    public boolean copy(Long originId, Long targetId) {
        // 复制原有的存储信息
        Resource origin = this.getByCurrentUser(originId);
        Assert.notNull(origin, "源资源不存在");
        // 获取 target
        Resource target;
        if (targetId.equals(ROOT_ID)) {
            target = new Resource().setId(ROOT_ID).setDir(true);
        } else {
            target = this.getByCurrentUser(targetId);
        }
        Assert.notNull(target, "目标资源不存在");
        Assert.isTrue(target.isDir(), "目标资源不是一个目录");
        // 校验操作
        this.checkMoveOrCopy(origin, target);
        Resource save = new Resource();
        // 复制被拷贝的数据
        BeanUtils.copyProperties(origin, save);
        save.setId(null);
        save.setParentId(target.getId());
        save.setCreateTime(LocalDateTime.now());
        if (origin.isDir()) {
            resourceMapper.insert(save);
            // 获取 origin 下的子文件
            List<Resource> children = resourceMapper.listByParentId(origin.getId());
            // 避免事物失效
            ((ResourceService) AopContext.currentProxy()).copy(children, save);
        } else {
            resourceMapper.insert(save);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean copy(List<Resource> resources, Resource parent) {
        Resource save;
        for (Resource child : resources) {
            // 保存每个子数据到新目录下
            save = new Resource();
            // 复制被拷贝的数据
            BeanUtils.copyProperties(child, save);
            save.setId(null);
            save.setParentId(parent.getId());
            save.setCreateTime(LocalDateTime.now());
            resourceMapper.insert(save);
            if (child.isDir()) {
                // 递归处理文件夹
                this.copy(resourceMapper.listByParentId(child.getId()), save);
            }
        }
        return true;
    }

    @Override
    public boolean move(Long originId, Long targetId) {
        Resource origin = this.getByCurrentUser(originId);
        Assert.notNull(origin, "源资源不存在");
        // 获取 target
        Resource target;
        if (targetId.equals(ROOT_ID)) {
            target = new Resource().setId(ROOT_ID).setDir(true);
        } else {
            target = this.getByCurrentUser(targetId);
        }
        Assert.notNull(target, "目标资源不存在");
        this.checkMoveOrCopy(origin, target);
        // 将源资源的 parentId 修改为目标 id
        Resource save = new Resource();
        BeanUtils.copyProperties(origin, save);
        save.setParentId(target.getId());
        return resourceMapper.updateById(save) == 1;
    }

    /**
     * 检查文件名是否合法
     * 检查目标文件夹下是否有重名文件
     * 检查 t 是否属于 o 的子文件
     *
     * @param origin
     * @param target
     */
    public void checkMoveOrCopy(Resource origin, Resource target) {
        // 目标目录和被复制的文件的父级需要不相同，否则抛出异常
        Assert.isFalse(target.getId().equals(origin.getParentId()), "源文件夹不可与目标文件夹相同");
        // 校验文件名在目标目录下是否唯一
        try {
            this.checkNameAndUnique(origin.getName(), target.getId(), target.getBucketId());
        } catch (CanShowException e) {
            throw new ResourceException("目标文件夹下有重名文件");
        }
        // 如果是根目录，校验 t 是否属于 o 的子文件
        if (origin.isDir() && !ROOT_ID.equals(target.getId())) {
            // 判断目标文件夹是否是源文件夹的子文件夹
            Assert.isFalse(isChildren(origin, target), "目标文件夹是源文件夹的子文件夹");
        }
    }

    /**
     * 判断 t 是否属于 o 的子文件 或 两个资源是否相等
     *
     * @param origin
     * @param target
     * @return
     */
    private boolean isChildren(Resource origin, Resource target) {
        if (null == target || null == target.getId()) return false;
        Long targetId = target.getId();
        if (ROOT_ID.equals(targetId)) return false;
            // 如果源 id == 目标 id
        else if (origin.getId().equals(targetId)) return true;
        // 获取 target 的父级目录
        return isChildren(origin, resourceMapper.selectById(target.getParentId()));
    }

    /**
     * 资源删除，当资源 hash 唯一时，同时删除磁盘文件
     * 删除操作会同时删除当前资源下的所有子文件
     *
     * @param resource
     * @return
     */
    @Override
    @Transactional
    public boolean delete(Resource resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        // 如果是目录，递归删除子文件
        if (db.isDir()) {
            QueryWrapper<Resource> wrapper = new QueryWrapper<>();
            wrapper.select("id", "type").eq("parent_id", db.getId());
            resourceMapper.selectList(wrapper).forEach(el -> {
                ((ResourceService) AopContext.currentProxy()).delete(el);
            });
        }
        resourceMapper.deleteById(db.getId());
        if (
            // 如果不为目录 && 当前资源没有引用，则删除本地资源
            !db.isDir() && (resourceMapper.countByHash(db.getHash()) == 0)) {
            File file = this.getLocalFile(db);
            if (file.exists() && file.delete()) {
                // 文件删除成功后删除数据库数据
                log.info("本地文件删除成功：{}", db.getName());
            }
        }
        return true;
    }

    /**
     * 通过文件路劲获取 File 对象
     * 注：仅会获取本地的文件
     *
     * @param resource
     * @return
     */
    @Override
    public File getLocalFile(Resource resource) {
        // 根据仓库地址和文件相对地址创建文件对象
        return new File(UploadConfig.LOCAL_PATH, resource.getPath());
    }

    @Transactional
    public boolean save(UploadDTO dto, Bucket bucket, @NonNull Long parentId) throws IOException {
        MultipartFile file = dto.getFile();
        // 根据 hash 获取数据库数据
        String hash = DigestUtil.md5Hex(file.getInputStream());
        this.checkNameAndUnique(file.getOriginalFilename(), parentId, bucket.getId()); // 与下面的判断重复
        // 检查文件是否唯一
        Resource db = this.getAndCheckHashInPath(hash, file.getOriginalFilename(), parentId, bucket.getId());
        Resource resource;
        String fileName = null;
        String path = suffixPath();
        if (null != db) {
            resource = new Resource();
            BeanUtils.copyProperties(db, resource);
            resource.setId(null);
        } else {
            fileName = IdUtil.simpleUUID() + "." + FileUtil.extName(file.getOriginalFilename());
            // 创建本地文件
            resource = new Resource();
            // 文件对应的本地存储路径
            resource.setPath(path + "/" + fileName);
            resource.setType(FileUtil.getType(file));
            resource.setDir(false);
            resource.setSize(file.getSize());
        }
        resource.setHash(hash);
        resource.setName(file.getOriginalFilename());
        resource.setBucketId(bucket.getId());
        resource.setUserId(bucket.getUserId());
        resource.setParentId(parentId);
        if (resourceMapper.insert(resource) == 1) {
            // 转存至本地文件
            FileStorageUtil.of(file.getBytes()).setPath(path).setFilename(fileName).upload();
            log.info("{}-文件保存成功，{}", dto.getIdentifier(), path);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当数据库存在同样的文件，执行复制操作
     * @return
     */
    @Override
    public Resource quickUpload(long resourceId, UploadDTO dto) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(dto.getBucketName());
        Long parentId = getIdByPath(bucket.getId(), dto.getPath());
        if (null == parentId) throw new ResourceException(dto.getPath() + "目录不存在");
        Resource db = resourceMapper.selectById(resourceId);
        Resource resource;
        Assert.notNull(db, "文件妙传失败");
        resource = new Resource();
        BeanUtils.copyProperties(db, resource);
        resource.setId(null);
        resource.setName(dto.getFilename());
        resource.setUserId(bucket.getUserId());
        resource.setParentId(parentId);
        resource.setBucketId(bucket.getId());
        resourceMapper.insert(resource);
        return resource;
    }

    /**
     * 文件上传
     *
     * @param upload
     * @return
     */
    @Override
    public String upload(UploadDTO upload) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(upload.getBucketName());
        Long parentId = getIdByPath(bucket.getId(), upload.getPath());
        if (null == parentId) throw new ResourceException(upload.getPath() + "目录不存在");
        MultipartFile file = upload.getFile();

        try {
            if (chunkService.save(upload)) {
                log.info("文件分块上传成功：{}, {}/{}", upload.getIdentifier(), upload.getChunkNumber(), upload.getTotalChunks());
            }
            // // 单文件上传
            // if (upload.getTotalChunks() == null || upload.getTotalChunks() <= 1) {
            //     // 存储数据
            //     this.save(upload, bucket, parentId);
            // } else { // 分块上传
            //     if (chunkService.save(upload)) {
            //         log.info("文件分块上传成功：{}, {}/{}", upload.getIdentifier(), upload.getChunkNumber(), upload.getTotalChunks());
            //     }
            // }
        } catch (Exception e) {
            log.error("文件上传失败");
            e.printStackTrace();
            throw new CanShowException(e.getMessage(), 400);
        }
        return file.getOriginalFilename();
    }

    /**
     * 文件合并请求
     * @param dto
     * @return
     */
    public Boolean merge(UploadDTO dto) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(dto.getBucketName());
        Long parentId = getIdByPath(bucket.getId(), dto.getPath());
        if (null == parentId) throw new ResourceException(dto.getPath() + "目录不存在");

        return chunkService.integrateFile(dto, bucket, parentId);
    }

    /**
     * 获取转存文件目的地地址
     *
     * @param filePath
     * @return
     */
    public static File transformFile(String filePath) {
        // 本地文件名格式：yyyy-MM/uuid.[fileType]
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        File dest = new File(UploadConfig.LOCAL_PATH, month);
        if (!dest.exists()) dest.mkdir();
        dest = new File(dest, filePath);
        return dest;
    }

    /**
     * 本地文件名前缀格式：yyyy-MM/filename
     * @return yyyy-MM
     */
    public static String suffixPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * 文件名校验
     *
     * @param fileName
     * @return
     */
    @Override
    public boolean checkName(String fileName) {
        if (StrUtil.isBlank(fileName)) return false;
        for (int i = 0; i < fileName.length(); i++) {
            if (ILLEGAL_CHAR.indexOf(fileName.charAt(i)) != -1) return false;
        }
        return true;
    }

    /**
     * 检查文件名是否合法
     * 检查文件名在目标文件夹下是否唯一
     *
     * @param fileName
     * @param parentId
     * @return
     */
    private boolean checkNameAndUnique(String fileName, Long parentId, Integer bucketId) {
        if (!this.checkName(fileName)) return false;
        // 如果是根目录，需要根据 bucketId 和 parentId 来进行检索
        resourceMapper.selectList(new QueryWrapper<Resource>().eq("bucket_id", bucketId).eq("parent_id", parentId).select("name")).forEach(el -> {
            if (fileName.equals(el.getName())) throw new ResourceException("当前目录下已有同名文件");
        });
        return true;
    }

    public boolean checkNameAndNoExist(String fileName, String path, int bucketId) {
        Long parentId = this.getIdByPath(bucketId, path);
        try {
            this.checkNameAndUnique(fileName, parentId, bucketId);
            return true;
        } catch (ResourceException e) {
            return false;
        }
    }

    /**
     * 上传前检查
     * 检查 文件hash/文件名 在当前目录下是否存在 重名/重复
     * @param dto
     * @return
     */
    @Override
    public Resource checkByHashOrNameInPath(UploadDTO dto) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(dto.getBucketName());
        Long parentId = getIdByPath(bucket.getId(), dto.getPath());
        if (null == parentId) throw new ResourceException(dto.getPath() + "目录不存在");
        // 如果存储目录下文件不存在
        // 查询数据库中是否有同样的文件
        return getAndCheckHashInPath(dto.getIdentifier(), dto.getFilename(), parentId, bucket.getId());
    }

    /**
     * 检查文件名是否合法
     * 检查文件名在目标文件夹下是否唯一
     * 检查是否有相同文件在当前目录下
     *
     * @param hash
     * @param filename
     * @param parentId
     * @param bucketId
     * @return
     */
    public Resource getAndCheckHashInPath(String hash, String filename, Long parentId, Integer bucketId) {
        // 检查是否同名
        try {
            Assert.isTrue(this.checkNameAndUnique(filename, parentId, bucketId), "文件名含有非法字符");
        } catch (ResourceException e) {
            throw e;
        }
        // 检查当前文件在数据库中是否存在
        Resource db = resourceMapper.selectByHash(hash);
        if (null != db) {
            /**
             * 校验文件在当前路径下是否存在
             * 如果 hash 冲突，且处于同一用户、同一仓库、同一目录，则拒绝本次提交
             */
            if (parentId.equals(ROOT_ID)) {
                Assert.isFalse(parentId.equals(db.getParentId()) && bucketId.equals(db.getBucketId()) && SecurityUtils.getLoginUserId().equals(db.getUserId()), "当前目录下已有相同文件-" + db.getName());
            } else {
                // 不是根路径只需要判断父级 id
                Assert.isFalse(parentId.equals(db.getParentId()), "当前目录下已有相同文件-" + db.getName());
            }
        }
        return db;
    }

    @Override
    public ResourceDTO download(ResourceDTO condition) {
        Resource resource = this.getByCurrentUser(condition.getId());
        if (null == resource || resource.isDir()) throw new ResourceException("当前分享的资源在地球找不到啦！");
        File file = this.getLocalFile(resource);
        if (!file.exists()) throw new ResourceException("当前分享的资源在地球找不到啦！1001");
        condition.setName(resource.getName());
        condition.setFileAbPath(file.getAbsolutePath());
        return condition;
    }

    @Override
    public ResourceDTO preview(ResourceDTO resourceDTO) {
        // 获取资源信息
        Resource resource = resourceMapper.selectByIdAndUser(resourceDTO.getId(), resourceDTO.getUserId());
        Assert.isTrue(resource != null, "资源已被删除");
        // 目录类型文件不进行预览
        Assert.isTrue(!resource.isDir(), "文件夹不允许预览");
        // 如果文件过大，不进行预览
        File file = this.getLocalFile(resource);
        resourceDTO.setName(resource.getName());
        resourceDTO.setType(resource.getType());
        resourceDTO.setSize(resource.getSize());
        resourceDTO.setFileAbPath(file.getAbsolutePath());
        return resourceDTO;
    }

    @Override
    public DlTaskInfo offlineDownload(String name, String url, int bucketId) {
        DlTaskInfo taskInfo = downloadService.build(name, url, 1L, bucketId).start();
        System.out.println(taskInfo.progress());
        return taskInfo;
    }

    @Override
    public List<Resource> search(ResourceDTO dto) {
        Long userId = SecurityUtils.getLoginUserId();
        Bucket bucket = bucketService.getBucket(dto.getBucketName());
        Assert.notNull(bucket, "bucket不存在");
        List<Resource> list = resourceMapper.selectAllByName(dto.getName(), userId, bucket.getId());
        // 获取每个资源的逻辑路径
        for (Resource resource : list) {
            List<ResourceParentDTO> r = resourceMapper.selectAllParent(resource.getId());
            String path = r.get(0).getViewPath();
            int i = path.lastIndexOf("/");
            resource.setPath(i == -1 ? "" : path.substring(0, i));
        }
        return list;
    }

    @Override
    public Resource createDownloadDir(long userId, int bucketId) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>();
        wrapper.eq("name", "download");
        wrapper.eq("bucket_id", bucketId);
        Resource db = resourceMapper.selectOne(wrapper);
        if (db != null) {
            return db;
        }
        Resource resource = new Resource();
        resource.setName("download");
        resource.setUserId(userId);
        resource.setBucketId(bucketId);
        resource.setType("dir");
        resource.setDir(true);
        resource.setParentId(0L);
        resourceMapper.insert(resource);
        return resource;
    }
}
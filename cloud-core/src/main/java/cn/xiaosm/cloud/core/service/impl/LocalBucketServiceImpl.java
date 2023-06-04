package cn.xiaosm.cloud.core.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.entity.Bucket;
import cn.xiaosm.cloud.core.mapper.BucketMapper;
import cn.xiaosm.cloud.core.mapper.ResourceMapper;
import cn.xiaosm.cloud.core.service.BucketService;
import cn.xiaosm.cloud.core.storage.UploadConfig;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Slf4j
@Service
public class LocalBucketServiceImpl extends ServiceImpl<BucketMapper, Bucket> implements BucketService {

    @Autowired
    BucketMapper bucketMapper;
    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public Bucket getLocalBucket() {
        Bucket bucket = new Bucket();
        bucket.setPath("/" + SecurityUtils.getLoginUser().getUuid());
        return bucket;
    }

    @Override
    public Bucket getBucket(String name) {
        Bucket bucket = this.getBucket(name, SecurityUtils.getLoginUserId());
        if (Objects.isNull(bucket)) throw new ResourceException(name + "-仓库不存在");
        return bucket;
    }

    @Override
    public Bucket getBucket(Integer bucketId) {
        if (null == bucketId) return null;
        return bucketMapper.selectById(bucketId);
    }

    @Override
    public Bucket getBucket(String name, Long userId) {
        return bucketMapper.selectByNameAndUserId(name, userId);
    }

    /**
     * 将 Bucket 转换转换成本地磁盘中映射的文件目录
     */
    @Override
    public File transformBucketToFile(Bucket bucket) {
        return FileUtil.file(UploadConfig.LOCAL_PATH);
    }

    @Override
    public boolean hasBucket(String name) {
        return Objects.nonNull(this.getBucket(name));
    }

}
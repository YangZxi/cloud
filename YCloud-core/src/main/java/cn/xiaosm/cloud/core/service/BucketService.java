package cn.xiaosm.cloud.core.service;

import cn.xiaosm.cloud.core.admin.service.BaseService;
import cn.xiaosm.cloud.core.entity.Bucket;

import java.io.File;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public interface BucketService extends BaseService<Bucket> {

    Bucket getLocalBucket();

    Bucket getBucket(String name);

    Bucket getBucket(Integer bucketId);

    Bucket getBucket(String name, Long userId);

    File transformBucketToFile(Bucket bucket);

    boolean hasBucket(String name);
}
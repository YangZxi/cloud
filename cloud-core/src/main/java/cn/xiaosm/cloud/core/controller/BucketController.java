package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.core.service.LocalBucketService;
import cn.xiaosm.cloud.core.service.ResourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Log4j2
@Api("bucket")
public class BucketController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    LocalBucketService bucketService;


}
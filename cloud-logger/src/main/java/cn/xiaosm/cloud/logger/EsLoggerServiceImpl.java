/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OperatorServiceImpl
 * Author:   Young
 * Date:     2020/6/16 10:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.logger;

import cn.xiaosm.cloud.logger.entity.EsLog;
import cn.xiaosm.cloud.common.entity.Log;
import cn.xiaosm.cloud.common.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class EsLoggerServiceImpl implements LoggerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ElasticService elasticService;

    @Override
    public boolean insert(Log log) {
        try {
            EsLog esLog = new EsLog();
            BeanUtils.copyProperties(log, esLog);
            return elasticService.insert(esLog);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
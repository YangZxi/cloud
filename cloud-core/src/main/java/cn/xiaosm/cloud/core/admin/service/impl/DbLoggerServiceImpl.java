package cn.xiaosm.cloud.core.admin.service.impl;


import cn.xiaosm.cloud.common.entity.Log;
import cn.xiaosm.cloud.common.service.LoggerService;
import cn.xiaosm.cloud.core.admin.entity.DbLog;
import cn.xiaosm.cloud.core.admin.mapper.LogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DbLoggerServiceImpl implements LoggerService {

    @Autowired
    LogMapper logMapper;

    @Override
    public boolean insert(Log log) {
        DbLog dbLog = new DbLog();
        BeanUtils.copyProperties(log, dbLog);
        return logMapper.insert(dbLog) == 1;
    }

}
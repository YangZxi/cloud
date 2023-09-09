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
package cn.xiaosm.cloud.core.admin.service.impl;

import cn.xiaosm.cloud.common.service.LoggerService;
import cn.xiaosm.cloud.core.admin.entity.DbLog;
import cn.xiaosm.cloud.core.admin.entity.vo.LogVO;
import cn.xiaosm.cloud.core.admin.entity.vo.Pager;
import cn.xiaosm.cloud.core.admin.mapper.LogMapper;
import cn.xiaosm.cloud.core.admin.service.LogService;
import cn.xiaosm.cloud.core.util.WrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, DbLog> implements LogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

    public boolean save(DbLog dbLog) {
        loggerService.insert(dbLog);
        logger.debug("记录日志=>{}", dbLog.getTitle());
        return true;
    }

    @Override
    public Pager<DbLog> listOfPage(Pager<DbLog> pager, LogVO log) {
        QueryWrapper<DbLog> wrapper = new QueryWrapper<>();
        WrapperUtils.bindSearch(wrapper, pager, "title");
        wrapper.eq("type", log.getType())
            .orderByDesc("create_time");
        // return logMapper.selectPage(pager, wrapper);
        return pager;
    }

}
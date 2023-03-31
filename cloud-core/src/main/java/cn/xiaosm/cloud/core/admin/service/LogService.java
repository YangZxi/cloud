/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OperatorService
 * Author:   Young
 * Date:     2020/6/16 10:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.service;

import cn.xiaosm.cloud.core.admin.entity.DbLog;
import cn.xiaosm.cloud.core.admin.entity.vo.LogVO;
import cn.xiaosm.cloud.core.admin.entity.vo.Pager;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public interface LogService {

    boolean save(DbLog dbLog);

    Pager<DbLog> listOfPage(Pager<DbLog> page, LogVO log);

}
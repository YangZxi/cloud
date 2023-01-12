/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: PropServiceImpl
 * Author:   Young
 * Date:     2020/6/18 16:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.admin.service.impl;

import cn.xiaosm.cloud.core.admin.service.PropService;
import cn.xiaosm.cloud.core.admin.entity.Prop;
import cn.xiaosm.cloud.core.admin.entity.vo.Pager;
import cn.xiaosm.cloud.core.admin.mapper.PropMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/18
 * @since 1.0.0
 */
@Service
public class PropServiceImpl extends ServiceImpl<PropMapper, Prop> implements PropService {

    @Autowired
    PropMapper propMapper;

    @Override
    public boolean update(Prop prop) {
        boolean update = this.update(prop, new UpdateWrapper<Prop>()
                .eq("prop_key", prop.getPropKey()));
        // 如果更新没有成功则进行插入操作
        if (!update) {
            prop.setId(null);
            update = this.save(prop);
        }
        return update;
    }

    @Override
    public Pager<Prop> listOfPage(Pager<Prop> pager, QueryWrapper<Prop> wrapper) {
        return null;
    }
}
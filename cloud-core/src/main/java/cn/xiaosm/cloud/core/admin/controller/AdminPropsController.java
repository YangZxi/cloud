package cn.xiaosm.cloud.core.admin.controller;

import cn.xiaosm.cloud.common.annotation.YAdmin;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.admin.entity.Prop;
import cn.xiaosm.cloud.core.admin.service.PropService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@YAdmin("api/props")
@RestController
public class AdminPropsController {

    @Autowired
    PropService propService;

    @GetMapping
    @PreAuthorize("hasAuthority('prop:query') or hasRole('admin')")
    public RespBody queryProps(Prop prop) {
        List<Prop> list = propService.list(new QueryWrapper<Prop>().eq("type", prop.getType()));
        return RespUtils.success(list);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('prop:add') or hasRole('admin')")
    public RespBody saveProp(@RequestBody List<Prop> props) {
        props.forEach(el -> el.setCreateTime(new Date()));
        boolean b = propService.saveBatch(props);
        return b ? RespUtils.success("保存成功")
                : RespUtils.fail("保存失败");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('prop:modify') or hasRole('admin')")
    public RespBody modifyProp(@RequestBody List<Prop> props) {
        boolean b = true;
        for (Prop prop : props) {
            b = propService.update(prop);
        }
        return b ? RespUtils.success("保存成功")
                : RespUtils.fail("修改失败");
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('prop:delete') or hasRole('admin')")
    public RespBody deleteProps(@RequestBody Set<Integer> ids) {
        boolean b = propService.removeByIds(ids);
        return b ? RespUtils.success("删除成功")
                : RespUtils.fail("删除失败");
    }

}
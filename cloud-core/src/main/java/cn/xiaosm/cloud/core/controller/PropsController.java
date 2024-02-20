package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.admin.entity.Prop;
import cn.xiaosm.cloud.core.admin.entity.enums.PropType;
import cn.xiaosm.cloud.core.admin.service.PropService;
import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Young
 * @create 2023/1/20
 * @since 1.0.0
 */
@Log4j2
@Api("props")
public class PropsController {

    @Autowired
    PropService propService;

    @GetMapping("filetype")
    @AnonymousAccess
    public RespBody queryProps() {
        List<Prop> list = propService.list(new QueryWrapper<Prop>().eq("type", PropType.FILE_TYPE));
        return RespUtils.success(list);
    }

}
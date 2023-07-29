package cn.xiaosm.cloud.core.admin.controller.oauth;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.admin.entity.UserOpen;
import cn.xiaosm.cloud.core.admin.service.impl.UserService;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public interface AuthLoginHandler {

    String BIND_PREFIX = "id=";
    UserService userService = null;

    /**
     * 平台的回调地址
     */
    String login(AuthCallback callback, HttpServletRequest request);

    /**
     * 解除绑定的操作
     */
    @RequestMapping("/revoke")
    default RespBody revokeAuth(UserOpen user) {
        if (user == null ||
            (user.getUserId() == null && user.getType() == null)) {
            return userService.revokeUserOpen(user) ?
                RespUtils.success("解绑成功")
                : RespUtils.fail("解绑失败");
        }
        return RespUtils.error("参数不完整");
    }

}
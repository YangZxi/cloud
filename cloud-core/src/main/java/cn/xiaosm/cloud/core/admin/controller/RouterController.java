package cn.xiaosm.cloud.core.admin.controller;

import cn.xiaosm.cloud.common.annotation.YAdmin;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.core.admin.entity.Menu;
import cn.xiaosm.cloud.core.admin.entity.enums.MenuType;
import cn.xiaosm.cloud.core.admin.entity.enums.StatusEnum;
import cn.xiaosm.cloud.core.admin.service.MenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@YAdmin
public class RouterController {

    @Autowired
    MenuService menuService;

    /**
     * 首页
     */
    @RequestMapping
    public String index(HttpServletRequest request) {
        if (request.getSession().getAttribute("USER") == null) {
            return "login";
        }
        // 获取当前用户的菜单
        List<Menu> menus = ((LoginUser) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getMenusOriginalOfList();
        // 删除多余菜单
        menus = menus.stream().filter(el -> el.getType() != MenuType.BUTTON).collect(Collectors.toList());
        request.setAttribute("menus", menuService.buildTree(menus));
        return "index";
    }

    /**
     * 首页
     */
    @RequestMapping("home")
    public String home() {
        return "home";
    }

    @RequestMapping("system/menu")
    public String menu(HttpServletRequest request) {
        // 获取当前用户的菜单
        List<? extends Menu> menus = ((LoginUser) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getMenus();
        try {
            // 过滤非启用状态的菜单
            List<? extends Menu> newList = menus.stream()
                    .filter(el -> StatusEnum.ENABLED.equals(el.getStatus()))
                    .toList();
            request.setAttribute("menus", new ObjectMapper().writeValueAsString(newList));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "system/menu";
    }

}
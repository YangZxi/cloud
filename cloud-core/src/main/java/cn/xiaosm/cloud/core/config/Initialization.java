package cn.xiaosm.cloud.core.config;

import cn.hutool.core.util.IdUtil;
import cn.xiaosm.cloud.common.util.SpringContextUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.admin.entity.Menu;
import cn.xiaosm.cloud.core.admin.entity.Prop;
import cn.xiaosm.cloud.core.admin.entity.Role;
import cn.xiaosm.cloud.core.admin.entity.User;
import cn.xiaosm.cloud.core.admin.entity.enums.MenuType;
import cn.xiaosm.cloud.core.admin.entity.enums.PropType;
import cn.xiaosm.cloud.core.admin.entity.enums.StatusEnum;
import cn.xiaosm.cloud.core.admin.service.*;
import cn.xiaosm.cloud.core.entity.Bucket;
import cn.xiaosm.cloud.core.entity.enums.BucketType;
import cn.xiaosm.cloud.core.service.LocalBucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Initialization implements ApplicationRunner {

    @Autowired
    PropService propService;
    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;
    @Autowired
    RoleService roleService;
    @Autowired
    LocalBucketService bucketService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SpringContextUtils.getBean(Initialization.class).install();
        loadMenu();
    }

    /**
     * 加载菜单
     */
    private void loadMenu() {
        // 项目启动完成后加载所有菜单
        MenuService menuService = SpringContextUtils.getBean(MenuService.class);
        // List<Menu> list = menuService.list(new QueryWrapper<Menu>().isNotNull("parent_menu"));
        CacheUtils.set("MenuList", menuService.list());
    }

    /**
     * 加载定时任务
     */
    private void loadTask() {
        // 项目启动完成后加载所有任务
        TaskService taskService = SpringContextUtils.getBean(TaskService.class);
        taskService.startAllTask();
    }

    @Transactional
    public void install() {
        Prop install = propService.selectByPropKey("install");
        if (null != install && install.getPropValue().equals("true")) {
            return;
        }
        // 初始化基本菜单数据
        initUser();
        initMenu();
        initRole();
        initRoleMenu();
        initUserRole();
        initProp();
        if (install == null) {
            install = new Prop();
            install.setPropKey("install");
            install.setType(PropType.APP_CONFIG);
        }
        install.setPropValue("true");
        install.setCreateTime(new Date());
        propService.saveOrUpdate(install);
    }

    private void initUser() {
        String password = userService.getDefaultPassword();
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, IdUtil.fastUUID(), "admin", password, "admin", "", "", StatusEnum.ENABLED));
        userList.add(new User(2L, IdUtil.fastUUID(), "guest", password, "游客", "", "", StatusEnum.ENABLED));
        userService.saveOrUpdateBatch(userList);
        log.info("初始化用户数据成功");

        // 初始化用户的 bucket
        List<Bucket> bucketList = new ArrayList<>();
        bucketList.add(new Bucket(1, "local", "本地存储", null, 1L, BucketType.LOCAL));
        bucketList.add(new Bucket(2, "local", "本地存储", null, 2L, BucketType.LOCAL));
        bucketService.saveOrUpdateBatch(bucketList);
        log.info("初始化用户的 bucket 成功");
    }

    private void initMenu() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(1, "根目录", MenuType.LEVEL_1, 1).setOrder(0));
        menuList.add(new Menu(2, "系统管理", MenuType.LEVEL_1, 1).setIcon("fa-gear"));
        menuList.add(new Menu(3, "角色管理", MenuType.PAGE, 1).setIcon("fa-user-plus"));
        menuList.add(new Menu(4, "菜单管理", MenuType.PAGE, 1).setIcon("fa-list"));
        menuList.add(new Menu(5, "资源管理", MenuType.PAGE, 1).setIcon("fa-archive"));
        menuList.add(new Menu(6, "定时任务", MenuType.PAGE, 1).setIcon("fa-clock"));

        menuList.add(new Menu(7, "系统监控", MenuType.LEVEL_1, 1).setIcon("fa-laptop"));
        menuList.add(new Menu(8, "操作日志", MenuType.PAGE, 1).setIcon("fa-laptop"));
        menuList.add(new Menu(9, "异常日志", MenuType.PAGE, 1).setIcon("fa-bug"));

        menuList.add(new Menu(10, "系统工具", MenuType.LEVEL_1, 1).setIcon("fa-toolbox"));
        menuList.add(new Menu(11, "SMTP管理", MenuType.PAGE, 1).setIcon("fa-envelope"));

        menuList.add(new Menu(12, "用户查询", MenuType.BUTTON, 2).setPermission("user:query"));
        menuList.add(new Menu(13, "用户添加", MenuType.BUTTON, 2).setPermission("user:add"));
        menuList.add(new Menu(14, "用户修改", MenuType.BUTTON, 2).setPermission("user:update"));
        menuList.add(new Menu(15, "用户删除", MenuType.BUTTON, 2).setPermission("user:delete"));

        menuList.add(new Menu(16, "角色查询", MenuType.BUTTON, 3).setPermission("role:query"));
        menuList.add(new Menu(17, "角色添加", MenuType.BUTTON, 3).setPermission("role:add"));
        menuList.add(new Menu(18, "角色修改", MenuType.BUTTON, 3).setPermission("role:update"));
        menuList.add(new Menu(19, "角色删除", MenuType.BUTTON, 3).setPermission("role:delete"));

        menuList.add(new Menu(20, "菜单查询", MenuType.BUTTON, 4).setPermission("menu:query"));
        menuList.add(new Menu(21, "菜单添加", MenuType.BUTTON, 4).setPermission("menu:add"));
        menuList.add(new Menu(22, "菜单修改", MenuType.BUTTON, 4).setPermission("menu:update"));
        menuList.add(new Menu(23, "菜单删除", MenuType.BUTTON, 4).setPermission("menu:delete"));

        menuList.add(new Menu(24, "操作日志查询", MenuType.BUTTON, 8).setPermission("log:query"));
        menuList.add(new Menu(25, "操作日志删除", MenuType.BUTTON, 8).setPermission("log:delete"));

        menuList.add(new Menu(26, "前端权限", MenuType.LEVEL_1, 1).setIcon("fa-tent"));
        menuList.add(new Menu(27, "资源上传", MenuType.BUTTON, 26).setPermission("front:resource:upload"));
        menuList.add(new Menu(28, "资源重命名", MenuType.BUTTON, 26).setPermission("front:resource:rename"));
        menuList.add(new Menu(29, "资源新建", MenuType.BUTTON, 26).setPermission("front:resource:create"));
        menuList.add(new Menu(30, "资源删除", MenuType.BUTTON, 26).setPermission("front:resource:delete"));
        menuList.add(new Menu(31, "资源预览与下载", MenuType.BUTTON, 26).setPermission("front:resource:download"));
        menuList.add(new Menu(32, "资源分享", MenuType.BUTTON, 26).setPermission("front:resource:share"));
        menuList.add(new Menu(33, "资源加速", MenuType.BUTTON, 26).setPermission("front:resource:cdn"));
        menuService.saveOrUpdateBatch(menuList);
        log.info("初始化菜单数据成功");
    }

    private void initRole() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(1, "ROLE_admin", "管理员", "-", StatusEnum.ENABLED));
        roleList.add(new Role(2, "ROLE_public", "游客用户", "-", StatusEnum.ENABLED));
        roleList.add(new Role(3, "ROLE_normal", "注册用户", "-", StatusEnum.ENABLED));
        roleService.saveOrUpdateBatch(roleList);
        log.info("初始化角色数据成功");
    }

    private void initRoleMenu() {
        Set<Integer> normalRoleMenuList = menuService.getByParentId(26).stream().map(Menu::getId).collect(Collectors.toSet());
        roleService.updateRoleMenu(normalRoleMenuList, 2);
        log.info("初始化角色菜单数据成功");
    }

    private void initUserRole() {
        // 管理员
        userService.updateUserRoles(1L, Set.of(1));
        // 游客
        userService.updateUserRoles(2L, Set.of(2));
        log.info("初始化用户角色数据成功");
    }

    private void initProp() {
        List<Prop> propList = new ArrayList<>();
        propList.add(new Prop("filetype_vide", "mp4,ogg", PropType.FILE_TYPE));
        propList.add(new Prop("filetype_image", "jpg,jpeg,gif,png", PropType.FILE_TYPE));
        propList.add(new Prop("filetype_text", "txt,md,js,java,cpp,c,py,go", PropType.FILE_TYPE));
        propList.add(new Prop("filetype_audio", "mp3,ogg", PropType.FILE_TYPE));
        propService.saveOrUpdateBatch(propList);
    }
}

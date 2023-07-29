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
import cn.xiaosm.cloud.core.admin.service.MenuService;
import cn.xiaosm.cloud.core.admin.service.PropService;
import cn.xiaosm.cloud.core.admin.service.RoleService;
import cn.xiaosm.cloud.core.admin.service.TaskService;
import cn.xiaosm.cloud.core.admin.service.impl.UserService;
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
    private MagicVar magicVar;

    @Override
    public void run(ApplicationArguments args) {
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
        magicVar = new MagicVar();
        initMenu();
        initUser();
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

    private static class MagicVar {
        private final long ADMIN_USER_ID = 1L;
        private final long GUEST_USER_ID = 2L;
        private final int ADMIN_ROLE_ID = 1;
        private final int NORMAL_ROLE_ID = 2;
        private final int GUEST_ROLE_ID = 3;
        private final int ROOT_MENU_ID = 1;
        private final int FRONT_MENU_ID = 1000;
    }

    private void initMenu() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(magicVar.ROOT_MENU_ID, "根目录", MenuType.LEVEL_1, 1).setOrder(0));

        menuList.add(new Menu(magicVar.FRONT_MENU_ID, "前端权限", MenuType.LEVEL_1, 1).setIcon("fa-tent"));
        menuList.add(new Menu(null, "资源上传", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:upload"));
        menuList.add(new Menu(null, "资源重命名", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:rename"));
        menuList.add(new Menu(null, "资源新建", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:create"));
        menuList.add(new Menu(null, "资源删除", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:delete"));
        menuList.add(new Menu(null, "资源预览与下载", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:download"));
        menuList.add(new Menu(null, "资源分享", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:share"));
        menuList.add(new Menu(null, "资源加速", MenuType.BUTTON, magicVar.FRONT_MENU_ID).setPermission("front:resource:cdn"));
        menuService.saveOrUpdateBatch(menuList);
        log.info("初始化菜单数据成功");
    }

    private void initUser() {
        String password = userService.getDefaultPassword();
        List<User> userList = new ArrayList<>();
        userList.add(new User(magicVar.ADMIN_USER_ID, IdUtil.fastUUID(), "admin", password, "admin", "", "", StatusEnum.ENABLED));
        userList.add(new User(magicVar.GUEST_USER_ID, IdUtil.fastUUID(), "guest", password, "游客", "", "", StatusEnum.ENABLED));
        userService.saveOrUpdateBatch(userList);
        log.info("初始化用户数据成功");

        // 初始化用户的 bucket
        List<Bucket> bucketList = new ArrayList<>();
        bucketList.add(new Bucket(1, "local", "本地存储", null, magicVar.ADMIN_USER_ID, BucketType.LOCAL));
        bucketList.add(new Bucket(2, "local", "本地存储", null, magicVar.GUEST_USER_ID, BucketType.LOCAL));
        bucketService.saveOrUpdateBatch(bucketList);
        log.info("初始化用户的 bucket 成功");
    }

    private void initRole() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(magicVar.ADMIN_ROLE_ID, "ROLE_admin", "管理员", "-", StatusEnum.ENABLED));
        roleList.add(new Role(magicVar.NORMAL_ROLE_ID, "ROLE_normal", "注册用户", "-", StatusEnum.ENABLED));
        roleList.add(new Role(magicVar.GUEST_ROLE_ID, "ROLE_guest", "游客用户", "-", StatusEnum.ENABLED));
        roleService.saveOrUpdateBatch(roleList);
        log.info("初始化角色数据成功");
    }

    private void initRoleMenu() {
        Set<Integer> normalRoleMenuList = menuService.getByParentId(1000).stream().map(Menu::getId).collect(Collectors.toSet());
        roleService.updateRoleMenu(normalRoleMenuList, magicVar.NORMAL_ROLE_ID);
        log.info("初始化角色菜单数据成功");
    }

    private void initUserRole() {
        // 管理员
        userService.updateUserRoles(magicVar.ADMIN_USER_ID, Set.of(magicVar.ADMIN_ROLE_ID));
        // 游客
        userService.updateUserRoles(magicVar.GUEST_USER_ID, Set.of(magicVar.GUEST_ROLE_ID));
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

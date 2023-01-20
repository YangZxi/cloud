package cn.xiaosm.cloud;

import cn.hutool.core.io.FileTypeUtil;
import cn.xiaosm.cloud.common.util.SpringContextUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.admin.service.MenuService;
import cn.xiaosm.cloud.core.admin.service.TaskService;
import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 项目启动类
 *
 * @copyright WWW.XIAOSM.CN
 * @author Young
 */
@EnableAsync // 启用异步方法调用
@EnableConfigurationProperties // 开启配置文件注入到实体类/属性
@EnableTransactionManagement // 开启事物
@EnableFileStorage
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true) // 开启 cglib, 暴露
// @MapperScan("cn.xiaosm.cloud.**.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        FileTypeUtil.removeFileType("52494646");
        loadMenu();
        // loadTask();
    }

    /**
     * 加载菜单
     */
    public static void loadMenu() {
        // 项目启动完成后加载所有菜单
        MenuService menuService = SpringContextUtils.getBean(MenuService.class);
        // List<Menu> list = menuService.list(new QueryWrapper<Menu>().isNotNull("parent_menu"));
        CacheUtils.set("MenuList", menuService.list());
    }

    /**
     * 加载定时任务
     */
    public static void loadTask() {
        // 项目启动完成后加载所有任务
        TaskService taskService = SpringContextUtils.getBean(TaskService.class);
        taskService.startAllTask();
    }

}

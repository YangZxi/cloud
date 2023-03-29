/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: InterceptorConfig
 * Author:   Young
 * Date:     2020/3/8 11:32
 * Description: 拦截器配置文件
 * History:
 */
package cn.xiaosm.cloud.core.config;

import cn.xiaosm.cloud.common.annotation.YAdmin;
import cn.xiaosm.cloud.core.admin.entity.dto.MenuDTO;
import cn.xiaosm.cloud.core.config.security.MyHandlerMethodArgumentResolver;
import cn.xiaosm.cloud.core.factory.BaseEnumConverterFactory;
import cn.xiaosm.cloud.core.interceptor.LogInterceptor;
import cn.xiaosm.cloud.core.interceptor.AdminInterceptor;
import cn.xiaosm.cloud.common.annotation.Api;
import cn.xiaosm.cloud.core.admin.service.MenuService;
import cn.xiaosm.cloud.core.interceptor.MainInterceptor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.*;

/**
 * 〈拦截器配置文件〉
 *
 * @author Young
 * @create 2020/3/8
 * @since 1.0.0
 */
@Log4j2
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    MenuService menuService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MyHandlerMethodArgumentResolver());
    }

    /**
     * 视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/index.html");
        // 注册菜单中的page页面
        List<MenuDTO> tree = menuService.getByParentIdOfTree(1, false);
        tree.forEach(menu -> {
            if (menu.getChildren() != null) {
                menu.getChildren().forEach(el -> {
                    String path = menu.getPath() + "/" + el.getPath();
                    registry.addViewController("admin/" + path).setViewName(path);
                });
            }
        });
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 静态资源处理器
     */
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        /**
         - /**： 匹配所有路径 /**
         - /api/*：只匹配 /api下的内容，不匹配/api/v1/*
         - /api/v1/**：匹配 /api/v1/ 下的所有路径
         */
        List<String> excludePath = new LinkedList<>(Arrays.asList(
            "/**/*.html", "/**/*.json", "/**/*.css", "/**/*.js",
            "/**/*.map", "/**/*.woff", "/**/*.ttf", "/**/*.png",
            "/**/*.jpg", "/**/*.ico", "/**/*.gif", "/**/*.svg"
        ));
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("加载Main拦截器");
        registry.addInterceptor(new MainInterceptor())
            .addPathPatterns("/**");
        log.info("加载日志记录拦截器");
        registry.addInterceptor(new LogInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/login");
        registry.addInterceptor(new AdminInterceptor())
            .addPathPatterns("/admin/**");
    }

    /**
     * 转换器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new BaseEnumConverterFactory());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
            // 添加后台路径前缀
            .addPathPrefix("/admin", c -> c.isAnnotationPresent(YAdmin.class))
            // 前台接口前缀
            .addPathPrefix("/api", c -> c.isAnnotationPresent(Api.class))
        ;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return factory -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            factory.addErrorPages(error404Page);
        };
    }

}
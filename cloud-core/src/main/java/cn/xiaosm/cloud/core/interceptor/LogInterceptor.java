/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MainInterceptor
 * Author:   Young
 * Date:     2020/3/8 11:24
 * Description: 核心拦截器
 * History:
 */
package cn.xiaosm.cloud.core.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉
 * 〈日志拦截器〉
 *
 * @author Young
 * @create 2020/6/25
 * @since 1.0.0
 */
public class LogInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ThreadLocal<Long> timer = new ThreadLocal<>();

    /**
     * 在DispatcherServlet之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断请求的所有时间
        timer.set(System.currentTimeMillis());
        return true;
    }

    /**
     * 在Controller执行之后的DispatcherServlet之后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!"/error".equals(request.getRequestURI())) {
            logger.info("客户端IP=[{}]，请求地址=[{}]，请求类型=[{}]，本次请求耗时={}ms",
                ServletUtil.getClientIP(request),
                request.getRequestURI(), request.getMethod(),
                System.currentTimeMillis() - timer.get());
        }
        timer.remove();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
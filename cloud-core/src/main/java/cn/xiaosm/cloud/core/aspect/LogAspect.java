package cn.xiaosm.cloud.core.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.common.entity.Log;
import cn.xiaosm.cloud.common.service.LoggerService;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.core.admin.entity.LoginUser;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
// @Order(100)
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    TokenService tokenService;
    @Autowired
    LoggerService loggerService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(cn.xiaosm.cloud.common.annotation.LogRecord)")
    public void logPointCut() { }

    //切面 环绕通知
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // System.out.println("环绕。。。。。");
        //保存日志
        Log esLog = new Log();
        // 设置请求IP
        esLog.setIp(ServletUtil.getClientIP(ServletUtils.getRequest()));
        // 设置请求者
        if (ServletUtils.getRequest().getUserPrincipal() instanceof LoginUser user) {
            esLog.setUserId(user.getId());
        }
        // 设置请求参数
        esLog.setContent(JSONUtil.toJsonStr(joinPoint.getArgs()));
        // 获取操作名称
        esLog.setTitle(((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogRecord.class).value());
        // 设置被调用的方法名称
        String className = joinPoint.getSignature().getDeclaringTypeName();
        esLog.setMethod(className + "." + joinPoint.getSignature().getName());
        // 执行方法 得到的对象是返回值
        Object returnObj;
        try {
            returnObj = joinPoint.proceed();
            esLog.setType("INFO");
            logger.info("{}", esLog);
        } catch (Exception e) {
            esLog.setType("ERROR");
            esLog.setError(e.getMessage());
            logger.error("{}", esLog);
            throw e;
        } finally {
            // 设置请求耗时
            esLog.setTime((int) (System.currentTimeMillis() - start));
            esLog.setCreateTime(LocalDateTime.now());
            loggerService.insert(esLog);
        }
        return returnObj;
    }

}

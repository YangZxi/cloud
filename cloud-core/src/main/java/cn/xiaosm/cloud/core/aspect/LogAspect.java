package cn.xiaosm.cloud.core.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.common.entity.Log;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.exception.ResourceException;
import cn.xiaosm.cloud.common.service.LoggerService;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.common.annotation.LogRecord;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Principal;
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
        if (ServletUtils.getRequest().getUserPrincipal() instanceof UsernamePasswordAuthenticationToken token) {
            esLog.setUserId((Long) token.getCredentials());
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
            returnObj= joinPoint.proceed();
            esLog.setType("INFO");
        } catch (CanShowException e) {
            esLog.setType("ERROR");
            esLog.setError(this.getStackTrace(e));
            throw e;
        } catch (Throwable throwable) {
            esLog.setType("ERROR");
            esLog.setError(this.getStackTrace(throwable));
            throwable.printStackTrace();
            throw throwable;
        } finally {
            // 设置请求耗时
            esLog.setTime((int) (System.currentTimeMillis() - start));
            // logService.save(log);
            esLog.setCreateTime(LocalDateTime.now());
            logger.info("{}", esLog);
            loggerService.insert(esLog);
        }
        throw new ResourceException("123");
        // return returnObj;
    }

    public String getStackTrace(Throwable e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        e.printStackTrace(ps);
        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ps.close();
        return out.toString();
    }

}

/**
 * Copyright: 2019-2020 WWW.XIAOSM.CN
 * FileName:    ValidtedExceptionHandler
 * Author:      Young
 * Date:        2020/4/7 16:25
 * Description:
 * History:
 */
package cn.xiaosm.cloud.core.config;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.entity.enums.RespStatus;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.util.RespUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/4/7
 * @version 1.0.0
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBody catchException(Throwable e) {
        e.printStackTrace();
        log.error("catchException: {}", e.getMessage());
        return RespUtils.error("请求出错，请稍后再试\n" + e.getMessage());
    }

    /**
     * 实体类参数验证错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBody catchValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error("catchValidException: {}", e.getMessage());
        return RespUtils.error(e.getBindingResult().getFieldError().getDefaultMessage(), null);
    }

    @ExceptionHandler(AuthenticationException.class)
    public RespBody catchLoginException(AuthenticationException e) {
        e.printStackTrace();
        log.error("catchLoginException: {}", e.getMessage());
        return RespUtils.error(e.getMessage(), null);
    }

    /**
     * 没有访问权限。使用 @PreAuthorize 校验权限不通过时，就会抛出 AccessDeniedException 异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public RespBody catchAuthorizationException(AccessDeniedException e) {
        e.printStackTrace();
        log.error("catchAuthorizationException: {}", e.getMessage());
        return RespUtils.build(RespStatus.AUTHORITIES_DENIED, "非法权限访问");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public RespBody catchUsernameNotFoundException(UsernameNotFoundException e) {
        e.printStackTrace();
        log.error("catchUsernameNotFoundException: {}", e.getMessage());
        return RespUtils.error(e.getCause().getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public RespBody illegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        log.error("illegal: {}", e.getMessage());
        return RespUtils.error(e.getCause().getMessage());
    }

    @ExceptionHandler(CanShowException.class)
    public ResponseEntity<RespBody> catchCanShowException(CanShowException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResponseEntity<>(RespUtils.error(e.getMessage()), e.getStatus());
    }

}
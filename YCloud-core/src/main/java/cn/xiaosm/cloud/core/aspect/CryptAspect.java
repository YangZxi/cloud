package cn.xiaosm.cloud.core.aspect;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.security.service.EncryptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class CryptAspect {

    @AfterReturning(value = "@annotation(cn.xiaosm.cloud.security.annotation.Encrypt)", returning = "res")
    public void after(JoinPoint joinPoint, Object res) throws Exception {
        if (Objects.nonNull(res) && res instanceof RespBody r) {
            if (r.getData() instanceof String s) {
                r.setData(EncryptService.encrypt(s));
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                String s = Objects.isNull(r.getData()) ? "" : objectMapper.writeValueAsString(r.getData());
                r.setData(EncryptService.encrypt(s));
            }
        }
    }

}

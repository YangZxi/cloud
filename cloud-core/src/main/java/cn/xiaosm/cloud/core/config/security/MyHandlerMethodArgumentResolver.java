package cn.xiaosm.cloud.core.config.security;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.core.admin.entity.dto.LoginUserDTO;
import cn.xiaosm.cloud.security.annotation.Decrypt;
import cn.xiaosm.cloud.security.service.EncryptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.MethodParameter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;

/**
 * @author Young
 * @create 2023/3/29
 * @since 1.0.0
 */
public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Decrypt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取修饰的参数名称
        String paramName = parameter.getParameterName();
        // 获取被修饰参数接收的值
        HttpRequestHandler request = webRequest.getNativeRequest(HttpRequestHandler.class);
        EncryptData data = JSONUtil.toBean(getRequestBody(webRequest), EncryptData.class);
        String body = EncryptService.decrypt(data.data);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, Class.forName(parameter.getParameter().getParameterizedType().getTypeName()));
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = "";
        try {
            assert servletRequest != null;
            jsonBody = IoUtil.read(servletRequest.getInputStream(), Charset.defaultCharset());
            webRequest.setAttribute("data", jsonBody, NativeWebRequest.SCOPE_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonBody;
    }

    @Data
    static class EncryptData {
        String data;
    }
}
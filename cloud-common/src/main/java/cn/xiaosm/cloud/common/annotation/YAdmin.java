package cn.xiaosm.cloud.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Controller
@RequestMapping
public @interface YAdmin {
    @AliasFor(
        value = "path",
        annotation = RequestMapping.class
    )
    String[] value() default {};

    @AliasFor(
        value = "value",
        annotation = Controller.class
    )
    String name() default "";
}
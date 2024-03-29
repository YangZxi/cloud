package cn.xiaosm.cloud.core.config;

import cn.xiaosm.cloud.common.service.LoggerService;
import cn.xiaosm.cloud.core.admin.service.impl.DbLoggerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Young
 * @create 2023/3/30
 * @since 1.0.0
 */
@Configuration
public class BeanConfig {

    @Bean
    public LoggerService getLoggerService() {
        return new DbLoggerServiceImpl();
    }

}
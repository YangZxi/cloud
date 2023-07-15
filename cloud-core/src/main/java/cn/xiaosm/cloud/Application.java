package cn.xiaosm.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync // 启用异步方法调用
@EnableConfigurationProperties // 开启配置文件注入到实体类/属性
@EnableTransactionManagement // 开启事物
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true) // 开启 cglib, 暴露
// @MapperScan("cn.xiaosm.cloud.**.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

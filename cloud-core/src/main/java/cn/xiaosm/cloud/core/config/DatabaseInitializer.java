package cn.xiaosm.cloud.core.config;

import cn.xiaosm.cloud.Application;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class DatabaseInitializer {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    private final DataSource dataSource;

    @PostConstruct
    public void initializeDatabase() {
        File databaseFile = new File(databaseUrl.replace("jdbc:sqlite:", ""));
        if (!databaseFile.exists()) {
            createDatabase(databaseFile);
            executeInitSql();
        }
    }

    private void createDatabase(File databaseFile) {
        try {
            if (!databaseFile.getParentFile().exists()) {
                Assert.isTrue(databaseFile.getParentFile().mkdirs(), "Failed to create parent directories");
            }
            Assert.isTrue(databaseFile.createNewFile(), "Failed to create database file");
        } catch (Exception e) {
            // Handle exception
            log.error("Failed to create database file, {}", e.getMessage());
            throw new RuntimeException("Failed to create database file", e);
        }
    }

    private void executeInitSql() {
        try {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("init/init.sql"));
            populator.execute(dataSource);
        } catch (Exception e) {
            // Handle exception
        }
    }
}

package com.nexusy.oauth.server.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lan
 * @since 2016-05-23
 */
@Configuration
public class DataSourceConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("url", "jdbc:mysql://localhost/oauth");

        return new HikariDataSource(config);
    }
}

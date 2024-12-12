package com.ti9.send.email.core.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JpaDatabaseConfig {
    @Value("${DATABASE_URL}")
    private String jdbcURL;

    @Value("${DATABASE_USERNAME}")
    private String user;

    @Value("${DATABASE_PASSWORD}")
    private String password;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcURL);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}

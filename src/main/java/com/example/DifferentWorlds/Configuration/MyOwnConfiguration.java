package com.example.DifferentWorlds.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.statemachine.config.EnableStateMachine;


import javax.sql.DataSource;

@EnableStateMachine
@Configuration
@EnableWebSecurity
// TODO this is not a well explained class name the class name should explain what it contains
public class MyOwnConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                .type(dataSourceProperties.getType())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Value("${custom.myapp.version}")
    private String version;
    @Value("${custom.myapp.name}")
    private String name;
    @Value("${custom.myapp.roles}")
    private String roles;

    public void printInfo(){
        System.out.println("my app name "+ name);
        System.out.println("my app version "+ version);
        System.out.println("roles in my app "+ roles);
    }

}

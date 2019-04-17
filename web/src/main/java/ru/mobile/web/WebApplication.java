package ru.mobile.web;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import ru.mobile.lib.rest.exception.GlobalControllerAdvice;

/**
 *
 * Created by OAKutsenko on 17.04.2019.
 */


@SpringBootApplication( exclude={DataSourceAutoConfiguration.class, org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class})
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
@Configuration
@ComponentScan({"ru.mobile.web", "ru.mobile.lib.service.firebase", "ru.mobile.lib.repository"})
@EnableFeignClients(basePackages = {"ru.mobile.lib.client"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.tomcat")
    public DataSource dataSource() {

        return new DataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public GlobalControllerAdvice globalControllerAdvice(){
        return new GlobalControllerAdvice();
    }

}

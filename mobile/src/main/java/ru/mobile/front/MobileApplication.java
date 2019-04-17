package ru.mobile.front;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import ru.mobile.lib.rest.exceptions.GlobalControllerAdvice;
import ru.mobile.lib.service.security.CustomUserInfoTokenServices;


/**
 *
 * Created by OAKutsenko on 20.12.2016.
 */


@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
@EnableAsync
@Configuration
//@ComponentScan({"ru.mobile.front", "ru.mobile.lib.rest.exception", "ru.mobile.lib.repository"})
public class MobileApplication extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerProperties sso;

    public static void main(String[] args) {
        SpringApplication.run(MobileApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.tomcat")
    public DataSource dataSource() {

        return new DataSource();
    }

    @Bean
    public GlobalControllerAdvice globalControllerAdvice(){
        return new GlobalControllerAdvice();
    }


    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/goods/topic" ).permitAll()
                .anyRequest().authenticated();
    }
}

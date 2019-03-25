package ru.mobile.auth;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.mobile.auth.service.security.CustomAuthenticationProvider;
import ru.mobile.auth.service.security.DbUserDetailsService;

import java.util.Arrays;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();
        // @formatter:on
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.tomcat")
    public DataSource dataSource() {

        return new DataSource();
    }

    @Autowired
    private DbUserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //return super.authenticationManagerBean();
        return new ProviderManager(Arrays.asList(new CustomAuthenticationProvider(userDetailsService)));
    }
}

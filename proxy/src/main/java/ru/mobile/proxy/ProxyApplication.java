package ru.mobile.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@Configuration
public class ProxyApplication {

	@Bean
	public CustomErrorFilter customErrorFilter(){
		return new CustomErrorFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}
}

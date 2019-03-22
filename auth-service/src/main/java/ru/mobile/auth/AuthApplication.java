package ru.mobile.auth;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.mobile.auth.exceptions.CustomWebResponseExceptionTranslator;
import ru.mobile.auth.service.security.CustomAuthenticationProvider;
import ru.mobile.auth.service.security.DbUserDetailsService;

import java.util.Arrays;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Bean
		@ConfigurationProperties(prefix="spring.datasource.tomcat")
		public DataSource dataSource() {

			return new DataSource();
		}

		@Autowired
		private DbUserDetailsService userDetailsService;


		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
					.authorizeRequests().anyRequest().authenticated()
					.and()
					.csrf().disable();
			// @formatter:on
		}

		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			//return super.authenticationManagerBean();
			return new ProviderManager(Arrays.asList(new CustomAuthenticationProvider(userDetailsService)));
		}
	}


	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private DbUserDetailsService userDetailsService;

		@Autowired
		private AuthenticationManager authenticationManager;


		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
					.withClient("acme")
					.secret("acmesecret")
					.authorizedGrantTypes("client_credentials", "password", "refresh_token")
					.scopes("read", "write")
					.authorities("ROLE_APP")
					.accessTokenValiditySeconds(120)
            .and()
					.withClient("service-account")
                    .secret("HUjsdDsfd@js$s83ByNlRngvZOA12")
					//.secret(env.getProperty("FRONT_SERVICE_PASSWORD"))
					.authorizedGrantTypes("client_credentials", "refresh_token")
					.scopes("server")
			.and()
					.withClient("web-service-account")
					.secret("H%^VVG332fd@js$s83ByNlRngFTsvcq15")
					.authorizedGrantTypes("client_credentials", "refresh_token")
					.authorities("ROLE_WEB")
					.scopes("web-server");
		}

/*
		@Autowired
		private RedisConnectionFactory redisConnectionFactory;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
			tokenStore.setPrefix("oauth2:");
			endpoints
					.tokenStore(tokenStore)
					.reuseRefreshTokens(false)
					.authenticationManager(authenticationManager)
					.userDetailsService(userDetailsService);
		}


*/
        private TokenStore tokenStore = new InMemoryTokenStore();

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
					.exceptionTranslator(webResponseExceptionTranslator());
        }

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer
					.tokenKeyAccess("permitAll()")
					.checkTokenAccess("isAuthenticated()");
		}

		@Bean
		public WebResponseExceptionTranslator webResponseExceptionTranslator() {
			return new CustomWebResponseExceptionTranslator();
		}
	}
}

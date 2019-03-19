package ru.mobile.lib.service.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Extended implementation of {@link org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices}
 *
 * By default, it designed to return only user details. This class provides {@link #getRequest(Map)} method, which
 * returns clientId and scope of calling service. This information used in controller's security checks.
 */

public class CustomUserInfoTokenServices implements ResourceServerTokenServices {

	protected final Log logger = LogFactory.getLog(getClass());

	private static final String[] PRINCIPAL_KEYS = new String[] { "user", "username",
			"userid", "user_id", "login", "id", "name" };

	private final String userInfoEndpointUrl;

	private final String clientId;

	private OAuth2RestOperations restTemplate;

	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	private AuthoritiesExtractor authoritiesExtractor = new OAuth2AuthoritiesExtractor();

	public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
		this.userInfoEndpointUrl = userInfoEndpointUrl;
		this.clientId = clientId;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setRestTemplate(OAuth2RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
		this.authoritiesExtractor = authoritiesExtractor;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {

		Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken) ;

		if (map.containsKey("error")) {
			this.logger.debug("userinfo returned error: " + map.get("error"));

			InvalidTokenException ex =  new InvalidTokenException(accessToken);
			ex.addAdditionalInformation("resultCode", "115");
			throw ex;
		}
		return extractAuthentication(map);
	}

	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {

		Object principal = getPrincipal(map);
		OAuth2Request request = getRequest(map);
		List<GrantedAuthority> authorities = this.authoritiesExtractor
				.extractAuthorities(map);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, "N/A", authorities);
		token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}

	private Object getPrincipal(Map<String, Object> map) {
		for (String key : PRINCIPAL_KEYS) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
		}
		return "unknown";
	}

	@SuppressWarnings({ "unchecked" })
	private OAuth2Request getRequest(Map<String, Object> map) {
		Map<String, Object> request = (Map<String, Object>) map.get("oauth2Request");

		String clientId = (String) request.get("clientId");
		Set<String> scope = new LinkedHashSet<>(request.containsKey("scope") ?
				(Collection<String>) request.get("scope") : Collections.<String>emptySet());

		return new OAuth2Request(null, clientId, null, true, new HashSet<>(scope),
				null, null, null, null);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> getMap(String path, String accessToken){
		this.logger.debug("Getting user info from: " + path);
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
					.getAccessToken();
			if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
						accessToken);
				token.setTokenType(this.tokenType);
				restTemplate.getOAuth2ClientContext().setAccessToken(token);
			}

			/* Add phone to session attr */
			final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			final HttpServletRequest request = (attr.getRequest());
			final Map<String, Object> map = restTemplate.getForEntity(path, Map.class).getBody();
/*
			for ( Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object tab = entry.getValue();
				logger.error("=== :"+key+" : "+tab);
			}
*/
			if (!request.getRequestURI().equals("/p4p/user/1154")){
				if ( map.get("principal") instanceof Map){
					final Map<String, String> principal = (Map<String, String>) map.get("principal");
					if (principal.containsKey("phone"))
						request.setAttribute("phone", principal.get("phone"));
				}
			}

			return map;
		}
		catch (Exception ex) {
			this.logger.info("Could not fetch user details: " + ex.getClass() + ", "
					+ ex.getMessage());

			return Collections.<String, Object>singletonMap("error",
					"Could not fetch user details");
		}
	}
}

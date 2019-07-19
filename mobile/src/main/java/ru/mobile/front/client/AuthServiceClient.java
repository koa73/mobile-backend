package ru.mobile.front.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mobile.lib.client.config.FeignCloudConfig;
import ru.mobile.front.domain.Candidate;


@FeignClient(
		name = "auth-service",
        configuration = FeignCloudConfig.class
)
public interface AuthServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/uaa/users/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	String createUser(Candidate candidate);

	//@RequestMapping(method = RequestMethod.POST, value = "/uaa/users/chg", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	//boolean changePassword(UserVerify user);
}

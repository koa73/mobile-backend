package ru.mobile.lib.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mobile.lib.client.config.FeignSimpleConfig;

import java.util.Map;

/**
 *
 * Created by Олег on 25.07.2017.
 */

@FeignClient(
        name="google",
        url="${firebase.client_x509_cert_url}",
        configuration = FeignSimpleConfig.class
)
public interface GoogleCertClient {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> getActualCerts();
}

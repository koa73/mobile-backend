package ru.mobile.auth.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

/**
 * Created by OAKutsenko on 26.01.2017.
 */
public class CustomWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public CustomWebResponseExceptionTranslator() {
        super();
    }

    @Override
    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        super.setThrowableAnalyzer(throwableAnalyzer);
    }

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
        OAuth2Exception body = responseEntity.getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(responseEntity.getHeaders().toSingleValueMap());

        // TODO something with header or response
        String resultCode = e.getMessage().replaceAll(".*@(\\d{3})$", "$1");

        if (resultCode.length() !=3){
            resultCode = "115";
        }
        log.error(body.toString());
        body.addAdditionalInformation("resultCode", resultCode);

        return new ResponseEntity<>(body, headers, responseEntity.getStatusCode());
    }
}

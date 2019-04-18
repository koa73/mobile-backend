package ru.mobile.lib.service.firebase;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mobile.lib.client.GoogleCertClient;
import ru.mobile.lib.rest.exception.RestApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 *
 * Created by OAKutsenko on 25.07.2017.
 */

@Service
public class JWTParserImpl implements JWTParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${firebase.client_x509_cert_url}")
    private String url;

    @Value("${firebase.iss}")
    private String iss;

    @Value("${firebase.aud}")
    private String aud;

    @Autowired(required = false)
    GoogleCertClient client;

    @Override
    public boolean phoneVerify(String phone, String idToken) {
        try {
            return phoneVerify(1020, phone, idToken);
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean phoneVerify(int cmdCode, String phone, String idToken) throws RestApiException{

        try {
            final Claims claims = Jwts.parser()
                    .requireIssuer(iss)
                    .requireAudience(aud)
                    .setSigningKeyResolver(new SigningKeyResolver() {
                        @Override
                        public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {

                            return getPublicKey(jwsHeader.get("kid") + "");
                        }

                        @Override
                        public Key resolveSigningKey(JwsHeader jwsHeader, String s) {
                            return null;
                        }

            }).parseClaimsJws(idToken).getBody();

            if (claims.getSubject() == null){

                log.error("Claim content error : " + "UID isn't setted.");
                throw new RestApiException(116, "UID error.");
            }

            final String phoneVerified = String.valueOf(claims.get("phone_number"));

            return phoneVerified.equals("+" + phone);

        } catch (MissingClaimException e) {

            log.error("Claim content error : " + e.getMessage());
            throw new RestApiException(116, e.getLocalizedMessage());

        } catch (IncorrectClaimException e) {

            log.error("Incorrect Claim error. Reason: " + e.getMessage());
            throw new RestApiException(116, e.getLocalizedMessage());


        } catch (SignatureException e){

            log.error("JWT Signature error : " + e.getMessage());
            throw new RestApiException(116, e.getLocalizedMessage());

        } catch (Exception e){

            log.error("JWT parse uncnown error. Reason: " + e.getMessage());
            throw new RestApiException(116, e.getLocalizedMessage());
        }
    }

    private PublicKey getPublicKey(String kid) {

        ResponseEntity<Map<String, String>> response = client.getActualCerts();

        String publicKey = response.getBody().get(kid);

        if (response.getStatusCode() == HttpStatus.OK && publicKey != null) {

            try {

                CertificateFactory fact = CertificateFactory.getInstance("X.509");
                InputStream stream = new ByteArrayInputStream(publicKey.getBytes(StandardCharsets.UTF_8));
                X509Certificate cer = (X509Certificate) fact.generateCertificate(stream);
                return  cer.getPublicKey();

            } catch (Exception e){

                log.error("Key convert error :" + e.getMessage());
                return null;
            }
        } else {

            log.error("Something wrong happened  ");
            return null;
        }
    }
}

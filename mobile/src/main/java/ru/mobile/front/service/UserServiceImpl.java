package ru.mobile.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mobile.front.client.AuthServiceClient;
import ru.mobile.front.domain.Candidate;
import ru.mobile.front.rest.model.UserCreateReq;
import ru.mobile.front.rest.view.UserCreateResp;
import ru.mobile.lib.rest.exception.RestApiException;
import ru.mobile.lib.service.firebase.JWTParser;


/**
 *
 * Created by OAKutsenko on 19.07.2019.
 */

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    JWTParser jwtParser;

    @Autowired(required = false)
    AuthServiceClient authService;

    @Override
    public UserCreateResp registration(UserCreateReq request) throws RestApiException {
        try {

            UserCreateResp response = new UserCreateResp();

            log.error("1");
            if (jwtParser.phoneVerify(request.getPhone(), request.getCredential())){

                log.error("2");
                final Candidate candidate = new Candidate(request.getPhone(), request.getEmail(), request.getPassword());
                log.error("3");
                try {
                    log.error("4");
                    final String uid = authService.createUser(candidate);
                    if (uid != null){

                        response.setUser(uid);

                    } else {
                        throw new RestApiException(115, "Auth service error");
                    }

                } catch ( Exception e){

                    log.error(e.getCause()+", "+e.getMessage());
                    throw new RestApiException(111, e.getMessage().replaceAll(".*\n?.*\"message\":\"(.*)\",?.*","$1"));
                }

            } else {
                throw new RestApiException(116, "Phone verify credential is invalid.");
            }

            return response;

        } catch (RestApiException e) {

            log.error("---- Error "+e.getError());
            throw e;

        } catch (Exception e){

            log.error("Registration user error. Reason: "+ e.getMessage());
            throw new RestApiException(111, e.getMessage());
        }
    }
}

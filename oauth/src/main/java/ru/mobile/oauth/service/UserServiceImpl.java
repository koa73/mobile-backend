package ru.mobile.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mobile.oauth.domain.Candidate;
import ru.mobile.oauth.domain.PwdChange;
import ru.mobile.oauth.domain.User;
import ru.mobile.oauth.exceptions.AuthApiException;
import ru.mobile.oauth.repository.UserRepository;

/**
 *
 * Created by OAKutsenko on 12.10.2016.
 */

@Configuration
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("userData")
    private UserRepository repository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String createUser(Candidate candidate) {

        candidate.setPassword(encoder.encode(candidate.getPassword()));
        return repository.createUser(candidate);
    }

    @Override
    public boolean verifyUser(PwdChange request) throws AuthApiException {

        User userDetail = repository.getUserDetail(request.getUser());
        if (encoder.matches(request.getPassword(), userDetail.getPassword())){

            request.setNewPassword(encoder.encode(request.getNewPassword()));
            repository.changePassword(request);
            return true;
        }

        throw  new AuthApiException(104, "Bad password.");
    }
}

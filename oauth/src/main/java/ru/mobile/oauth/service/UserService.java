package ru.mobile.oauth.service;


import ru.mobile.oauth.domain.Candidate;
import ru.mobile.oauth.domain.PwdChange;
import ru.mobile.oauth.exceptions.AuthApiException;


/**
 *
 * Created by OAKutsenko on 12.10.2016.
 */

public interface UserService {

    String createUser(Candidate candidate);
    boolean verifyUser(PwdChange request) throws AuthApiException;
}

package ru.mobile.auth.service;


import ru.mobile.auth.domain.Candidate;
import ru.mobile.auth.domain.PwdChange;
import ru.mobile.auth.exceptions.AuthApiException;


/**
 *
 * Created by OAKutsenko on 12.10.2016.
 */

public interface UserService {

    String createUser(Candidate candidate);
    boolean verifyUser(PwdChange request) throws AuthApiException;
}

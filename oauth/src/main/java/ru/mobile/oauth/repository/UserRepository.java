package ru.mobile.oauth.repository;

import ru.mobile.oauth.domain.Candidate;
import ru.mobile.oauth.domain.PwdChange;
import ru.mobile.oauth.domain.User;

/**
 *
 * Created by OAKutsenko on 19.09.2016.
 */

public interface UserRepository {

    User getUserDetail(String uid);
    String createUser(Candidate candidate);
    void  resetTryCount(String uid);
    void  setTryCount(String uid);
    void changePassword(PwdChange request);
}

package ru.mobile.auth.repository;

import ru.mobile.auth.domain.Candidate;
import ru.mobile.auth.domain.PwdChange;
import ru.mobile.auth.domain.User;

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

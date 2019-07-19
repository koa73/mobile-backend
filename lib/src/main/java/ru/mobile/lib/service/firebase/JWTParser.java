package ru.mobile.lib.service.firebase;


import ru.mobile.lib.rest.exception.RestApiException;

/**
 *
 * Created by OAKutsenko on 25.07.2017.
 */
public interface JWTParser {

    public boolean phoneVerify(String phone, String idToken)throws RestApiException;
}

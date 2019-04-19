package ru.mobile.lib.service.firebase;


import ru.mobile.front.rest.exception.RestApiException;

/**
 *
 * Created by OAKutsenko on 25.07.2017.
 */
public interface JWTParser {

    public boolean phoneVerify(int cmdCode, String phone, String idToken) throws RestApiException;
    public boolean phoneVerify(String phone, String idToken);
}

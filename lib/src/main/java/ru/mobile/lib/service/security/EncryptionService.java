package ru.mobile.lib.service.security;


import ru.mobile.lib.rest.exception.RestApiException;

/**
 *
 * Created by OAKutsenko on 31.08.2017.
 */
public interface EncryptionService {

    String decryptValue(String encrypted);
    String encryptValue(String value, String userName);
}

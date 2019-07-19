package ru.mobile.front.service;


import ru.mobile.front.rest.model.UserCreateReq;
import ru.mobile.front.rest.view.UserCreateResp;
import ru.mobile.lib.rest.exception.RestApiException;

/**
 *
 * Created by OAKutsenko on 19.07.2019.
 */


public interface UserService {

    UserCreateResp registration(UserCreateReq request) throws RestApiException;
}

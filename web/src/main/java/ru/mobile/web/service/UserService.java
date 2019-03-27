package ru.mobile.web.service;

import java.util.Map;

/**
 * Created by oakutsenko on 02.02.2018.
 */
public interface UserService {

    Map<String,Object> getUserProfile(String phone);
}

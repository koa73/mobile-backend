package ru.mobile.web.repository;

import java.util.Map;

/**
 *
 * Created by oakutsenko on 21.03.2018.
 */
public interface UserDAO {

    Map<String, Object> getUserProfile(String phone);
}

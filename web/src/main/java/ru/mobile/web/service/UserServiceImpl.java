package ru.mobile.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.phone4pay.ui.repository.UserDAO;

import java.util.Map;

/**
 * Created by oakutsenko on 02.02.2018.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO repository;

    @Override
    public Map<String, Object> getUserProfile(String phone) {

        return repository.getUserProfile(phone);
    }
}

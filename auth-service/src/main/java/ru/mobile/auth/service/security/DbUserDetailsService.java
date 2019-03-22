package ru.mobile.auth.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mobile.auth.domain.User;
import ru.mobile.auth.repository.UserRepository;

/**
 *
 * Created by OAKutsenko on 19.09.2016.
 */

@Service
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("userData")
    private UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.getUserDetail(username);
    }

    public void resetTryCount(String username){
        repository.resetTryCount(username);
    }

    public void setTryCount(String username){
        repository.setTryCount(username);
    }
}

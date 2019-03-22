package ru.mobile.auth.service.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.mobile.auth.domain.User;

/**
 *
 * Created by OAKutsenko on 02.12.2016.
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private DbUserDetailsService service;

    public CustomAuthenticationProvider(DbUserDetailsService service){
        this.service = service;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String username = token.getName();
        final String password = token.getCredentials().toString(); // retrieve the password

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = service.loadUserByUsername(username);

        if (!user.isEnabled()) {
            throw new DisabledException("User is locked. @114 "+user.getPhone()+" : "+username); //114
        }
        if (!encoder.matches(password, user.getPassword())) {
            service.setTryCount(username);
            if (user.getTryCount() < 2) {
                throw new BadCredentialsException(user.getTryCount() + " Bad password. @104 "+user.getPhone()+" : "+username); //104
            } else {
                throw new DisabledException("User is locked. @114 "+user.getPhone()+" : "+username); //114
            }
        }

        user.setPassword(null);

        //if (user.getTryCount() > 0) {
            service.resetTryCount(username);
        //}


        UsernamePasswordAuthenticationToken response =
                new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        response.setDetails(authentication.getDetails());
        return response;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

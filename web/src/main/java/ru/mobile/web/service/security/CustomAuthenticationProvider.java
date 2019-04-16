package ru.mobile.web.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import ru.mobile.lib.service.firebase.JWTParser;


/**
 *
 * Created by oakutsenko on 15.02.2018.
 */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    JWTParser jwtParser;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        final String phone = auth.getName();
        final String credentials = auth.getCredentials().toString();
        final String[] roles = new String[] {"WEB_USER"};

        // validate credentials
        boolean success = true;

        if(!jwtParser.phoneVerify(phone, credentials)) {

            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(phone, null, AuthorityUtils.createAuthorityList(roles));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

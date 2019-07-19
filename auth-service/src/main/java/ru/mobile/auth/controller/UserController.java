package ru.mobile.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mobile.auth.domain.Candidate;
import ru.mobile.auth.domain.PwdChange;
import ru.mobile.auth.exceptions.AuthApiException;
import ru.mobile.auth.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/chg", method = RequestMethod.POST)
    public boolean changePassword(@RequestBody PwdChange request) throws AuthApiException{
        return userService.verifyUser(request);
    }


    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@RequestBody Candidate candidate) {
        log.error("12");
        return userService.createUser(candidate);
    }
}
package ru.mobile.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mobile.oauth.domain.Candidate;
import ru.mobile.oauth.domain.PwdChange;
import ru.mobile.oauth.exceptions.AuthApiException;
import ru.mobile.oauth.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

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

    //@PreAuthorize("#contact.name == authentication.name")
    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@RequestBody Candidate candidate) {
        return userService.createUser(candidate);
    }
}
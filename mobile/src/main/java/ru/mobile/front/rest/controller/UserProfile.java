package ru.mobile.front.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mobile.front.rest.model.UserCreateReq;
import ru.mobile.front.rest.view.UserCreateResp;
import ru.mobile.lib.rest.exceptions.RestApiException;

import javax.validation.constraints.Null;
import java.security.Principal;

@RestController
@RequestMapping(path = "/user",
        method = RequestMethod.POST,
        headers = {"Content-Type=application/json"},
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class UserProfile {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // Test !!!!!
    @RequestMapping(path = "/create")
    //@PreAuthorize("hasRole('ROLE_APP')")
    public UserCreateResp createUser(@RequestBody UserCreateReq request, Principal principal, BindingResult bindingResult)
            throws RestApiException {
        if (bindingResult.hasErrors())
            throw new RestApiException(bindingResult, 2000);

        log.error(principal.getName());

        return null;
    }
}

package ru.mobile.web.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mobile.web.config.Messages;
import ru.mobile.web.rest.exception.WebApiException;
import ru.mobile.web.service.UserService;

import java.security.Principal;


/**
 *
 * Created by Олег on 31.12.2017.
 */

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    Messages messages;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        if (error != null){
            throw new BadCredentialsException("Bad credentials");
        }
        return "login";
    }


    @RequestMapping(path = "/", method = RequestMethod.GET )
    public String test(Principal principal, Model model) throws WebApiException {

        //--- Logging ---
        log.error("User "+principal.getName()+" has logged.");

        model.addAttribute("user", principal.getName());
        return "main";
    }

}

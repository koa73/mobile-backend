package ru.mobile.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mobile.web.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


/**
 *
 * Created by Олег on 23.03.2018.
 */

@Controller
public class PageController {

    @Autowired
    UserService userService;


    @RequestMapping("/profile")
    public String getUserProfile (Principal principal, HttpServletResponse response, Model model) {
        response.addHeader("Location","/profile");

        model.addAttribute("user", userService.getUserProfile(principal.getName()));
        return "profile";
    }

}

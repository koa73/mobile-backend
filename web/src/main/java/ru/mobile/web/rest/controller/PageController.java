package ru.mobile.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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


    /* Show empty page */
    @RequestMapping("/operations")
    public String operationList(HttpServletResponse response) {
        response.addHeader("Location","/operations");
        return "operations";
    }

    @RequestMapping("/profile")
    public String getUserProfile (Principal principal, HttpServletResponse response, Model model) {
        response.addHeader("Location","/profile");

        model.addAttribute("user", userService.getUserProfile(principal.getName()));
        return "profile";
    }

}

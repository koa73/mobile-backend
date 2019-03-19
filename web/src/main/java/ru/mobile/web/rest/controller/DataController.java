package ru.mobile.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mobile.web.rest.exception.WebApiException;
import ru.mobile.web.service.DataService;

import javax.validation.Valid;
import java.security.Principal;

/**
 *
 * Created by Олег on 10.03.2018.
 */

@RestController
public class DataController {

    @Autowired
    DataService dataService;


    @RequestMapping(path = "/data/operations", method = RequestMethod.GET)
    public String operations(Principal principal) throws WebApiException {
        return dataService.getOperations(principal.getName());
    }
}

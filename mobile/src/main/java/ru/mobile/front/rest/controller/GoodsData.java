package ru.mobile.front.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mobile.front.rest.model.UserCreateReq;
import ru.mobile.front.rest.view.UserCreateResp;
import ru.mobile.lib.rest.exceptions.RestApiException;
import ru.mobile.lib.rest.exceptions.WebApiException;

import javax.validation.constraints.Digits;
import java.security.Principal;

@Controller
@Validated
@RequestMapping(path = "/goods",
        method = RequestMethod.POST,
        headers = {"Content-Type=application/json"},
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class GoodsData {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @RequestMapping(path = "/item")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public UserCreateResp getItemList(@RequestBody UserCreateReq request, Principal principal, BindingResult bindingResult)
            throws RestApiException {

        if (bindingResult.hasErrors())
            throw new RestApiException(bindingResult, 1001);



        return null;
    }

    @RequestMapping(path = "/topic")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getTopicList(@Digits(integer=3, fraction=1, message = "invalid topic_id value.")
                                                   @RequestParam("topic_id") int topic_id) throws RestApiException {

        log.error("Topic : "+topic_id);
        return ResponseEntity.ok(""+topic_id);
    }
}

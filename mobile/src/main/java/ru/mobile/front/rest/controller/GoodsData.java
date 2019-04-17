package ru.mobile.front.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mobile.front.rest.model.UserCreateReq;
import ru.mobile.front.rest.view.TopicsView;
import ru.mobile.front.rest.view.UserCreateResp;
import ru.mobile.lib.rest.exceptions.RestApiException;
import ru.mobile.lib.rest.exceptions.WebApiException;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/goods",
        method = RequestMethod.POST,
        //headers = {"Content-Type=application/json"},
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
    //@RequestBody String operId
    public ResponseEntity<String> getTopicList(@Size(min = 0, max = 2, message = "txtMsg") @RequestParam(name = "topic_id")
                                                     String topic_id) throws WebApiException {

        log.error("Topic : "+topic_id);
        return ResponseEntity.ok(""+topic_id);
    }
}

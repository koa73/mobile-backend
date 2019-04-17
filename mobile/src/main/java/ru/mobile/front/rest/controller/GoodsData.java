package ru.mobile.front.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mobile.front.rest.model.Topics;
import ru.mobile.front.rest.model.UserCreateReq;
import ru.mobile.front.rest.view.TopicsView;
import ru.mobile.front.rest.view.UserCreateResp;
import ru.mobile.lib.rest.exceptions.RestApiException;

import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@RestController
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
    public List<TopicsView> getTopicList(@Pattern(regexp = "\\d{1,2}",message="rid.") int topic_id, Principal principal, BindingResult bindingResult)
            throws RestApiException {

        if (bindingResult.hasErrors())
            throw new RestApiException(bindingResult, 1002);

        log.error("Topic : "+topic_id);


        return null;
    }
}

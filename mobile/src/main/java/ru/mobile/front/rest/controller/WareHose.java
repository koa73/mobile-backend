package ru.mobile.front.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mobile.front.service.WarehouseService;
import ru.mobile.lib.rest.exception.RestApiException;
import ru.mobile.lib.rest.validation.IntID;


import java.security.Principal;

@Controller
@Validated
@RequestMapping(path = "/goods",
        method = RequestMethod.POST,
        headers = {"Content-Type=application/json"},
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class WareHose {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    WarehouseService warehouseService;

    @RequestMapping(path = "/item")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getItemList(@IntID(min = 0, max = 3) @RequestParam("topic_id") int topic_id,
                                              Principal principal) throws RestApiException {

        return ResponseEntity.ok(warehouseService.getItems(topic_id));
    }

    @RequestMapping(path = "/topic")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getTopicList(@IntID(min = 0, max = 3) @RequestParam("topic_id") int topic_id,
                                               Principal principal) throws RestApiException {

        return ResponseEntity.ok(warehouseService.getTopics(topic_id));
    }
}

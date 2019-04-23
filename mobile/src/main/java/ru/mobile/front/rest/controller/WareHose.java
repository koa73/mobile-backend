package ru.mobile.front.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WareHose {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    WarehouseService warehouseService;

    @RequestMapping(path = "/goods/items", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getItemList(@PathVariable String topic_id,
                                              Principal principal) throws RestApiException {

        return ResponseEntity.ok(warehouseService.getItems(Integer.parseInt(topic_id)));
    }

    @RequestMapping(path = "/goods/topic", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getTopicList(@PathVariable String topic_id,
                                               Principal principal) throws RestApiException {

        return ResponseEntity.ok(warehouseService.getTopics(Integer.parseInt(topic_id)));
    }
}

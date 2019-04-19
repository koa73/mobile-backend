package ru.mobile.front.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mobile.front.config.Messages;
import ru.mobile.front.repository.WarehouseDAO;
import ru.mobile.lib.rest.exception.RestApiException;


@Service
public class WarehoseServiceImpl implements WarehouseService {

    @Autowired
    WarehouseDAO repository;

    @Autowired
    Messages messages;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String getTopics(int topic_id){

        return repository.getTopics(topic_id);
    }

    @Override
    public String getItems(int topic_id)throws RestApiException {

        log.error(">>>>>>          "+messages.get("error.105"));
        return repository.getItems(topic_id);
    }
}

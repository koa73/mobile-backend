package ru.mobile.front.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mobile.front.repository.WarehouseDAO;
import ru.mobile.lib.rest.exception.RestApiException;

import java.util.List;


@Service
public class WarehoseServiceImpl implements WarehouseService {

    @Autowired
    WarehouseDAO repository;

    @Override
    public String getTopics(int topic_id){

        return repository.getTopics(topic_id);
    }

    @Override
    public String getItems(int topic_id)throws RestApiException {
        return repository.getItems(topic_id);
    }
}

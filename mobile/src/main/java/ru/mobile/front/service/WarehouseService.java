package ru.mobile.front.service;


import ru.mobile.front.rest.exception.RestApiException;


public interface WarehouseService {

    String getTopics(int topic_id) throws  RestApiException;
    String getItems(int topic_id) throws RestApiException;
}

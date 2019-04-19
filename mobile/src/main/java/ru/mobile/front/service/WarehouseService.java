package ru.mobile.front.service;


import ru.mobile.lib.rest.exception.RestApiException;


public interface WarehouseService {

    String getTopics(int topic_id) throws  RestApiException;
    String getItems(int item_id) throws RestApiException;
}

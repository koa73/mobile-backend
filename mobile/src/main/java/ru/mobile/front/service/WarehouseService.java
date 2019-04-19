package ru.mobile.front.service;


public interface WarehouseService {

    String getTopics(int topic_id) throws  RestApiException;
    String getItems(int topic_id) throws RestApiException;
}

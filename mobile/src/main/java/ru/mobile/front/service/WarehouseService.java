package ru.mobile.front.service;


import ru.mobile.lib.rest.exception.RestApiException;

import java.util.List;

public interface WarehouseService {

    String getTopics(int topic_id);
    String getItems(int item_id) throws RestApiException;
}

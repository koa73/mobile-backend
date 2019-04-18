package ru.mobile.front.service;


import ru.mobile.front.rest.view.TopicsView;
import ru.mobile.lib.rest.exception.RestApiException;

import java.util.List;

public interface WarehouseService {

    List<TopicsView> getTopics(int topic_id);
    String getItems(int item_id) throws RestApiException;
}

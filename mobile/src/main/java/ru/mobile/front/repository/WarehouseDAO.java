package ru.mobile.front.repository;

import ru.mobile.front.rest.view.TopicsView;

import java.util.List;

public interface WarehouseDAO {

    List<TopicsView> getTopics(int topic_id);
}

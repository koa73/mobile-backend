package ru.mobile.front.repository;

import java.util.List;

public interface WarehouseDAO {

    String getTopics(int topic_id);
    String getItems(int topic_id);
    String getItemInfo(int item_id);
}

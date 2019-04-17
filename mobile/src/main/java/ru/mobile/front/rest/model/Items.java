package ru.mobile.front.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "item_id"
})
public class Items {

    @JsonProperty("item_id")
    private Integer topicId;

    @JsonProperty("item_id")
    public Integer getTopicId() {
        return topicId;
    }

    @JsonProperty("item_id")
    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}

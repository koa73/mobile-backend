package ru.mobile.front.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "topic_id"
})
public class Topics {

    @JsonProperty("topic_id")
    private Integer topicId;

    @JsonProperty("topic_id")
    public Integer getTopicId() {
        return topicId;
    }

    @JsonProperty("topic_id")
    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}

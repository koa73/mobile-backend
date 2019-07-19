package ru.mobile.front.rest.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "resultCode",
        "user",
        "errMsg"
})
public class UserCreateResp {

    @JsonProperty("user")
    private String user;

    public UserCreateResp() {
        super();
    }

    /**
     *
     * @return
     * The user
     */
    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

}
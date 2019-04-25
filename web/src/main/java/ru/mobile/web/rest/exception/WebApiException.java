package ru.mobile.web.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * Created by OAKutsenko on 12.10.2017.
 */

public class WebApiException extends Exception {

    @JsonIgnoreProperties(value =
            {"cause", "stackTrace", "localizedMessage", "suppressed", "message", "status"},
            ignoreUnknown=true)


    private int code;

    private String error;

    private String error_description;


    public WebApiException (){}

    public WebApiException (int code, String error){
        this.code = code;
        this.error = error;
        this.error_description = null;
    }


    public WebApiException (int code, String error, String error_description){
        this.code = code;
        this.error = error;
        this.error_description = error_description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    @Override
    public String toString() {

        return "{"+
                "\"code\":\""+code+
                "\", \"error\":\""+error +
                "\", \"error_description\":\""+error_description +
                "\"}";
    }
}

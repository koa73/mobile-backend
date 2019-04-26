package ru.mobile.auth.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;


@JsonIgnoreProperties(value =
        {"cause", "stackTrace", "localizedMessage", "suppressed",  "message", "status", "causeReason"},
        ignoreUnknown=true)
public class AuthApiException extends Exception {


    private int code;
    private String error;
    private String error_description;


    public AuthApiException(int code, String error){
        this.code = code;
        this.error = error;
        this.error_description = null;
    }


    public AuthApiException(int code, String error, String reason){
        this.code = code;
        this.error = error;
        this.error_description = reason;
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


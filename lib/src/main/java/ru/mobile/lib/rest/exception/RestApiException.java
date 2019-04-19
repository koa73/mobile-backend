package ru.mobile.lib.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@JsonIgnoreProperties(value =
        {"cause", "stackTrace", "localizedMessage", "suppressed",  "message", "status", "causeReason"},
        ignoreUnknown=true)

public class RestApiException extends Exception {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    private int code;
    private String error;
    private String reason;


    public RestApiException(int code, String message){
        this.code = code;
        this.error = message;
        this.reason = null;
    }


    public RestApiException(int code, String message, String reason){
        this.code = code;
        this.error = message;
        this.reason = reason;
    }


    public RestApiException(BindingResult result) {

        this.code = 101; // Bad JSON request values or format
        StringBuilder buffer=new StringBuilder();
        boolean isFirst=true;
        for(ObjectError error : result.getAllErrors()) {
            if(isFirst) {
                isFirst=false;
            } else {
                buffer.append(", ");
            }
            buffer.append(error.getDefaultMessage());
        }
        this.error="Received wrong value(s) : "+buffer.toString()+" ;";
    }


    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {

        return "{"+
                "\"code\":\""+code+
                "\", \"error\":\""+error +
                "\", \"reason\":\""+reason +
                "\"}";
    }

}


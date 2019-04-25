package ru.mobile.lib.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;


@JsonIgnoreProperties(value =
        {"cause", "stackTrace", "localizedMessage", "suppressed",  "message", "status", "causeReason"},
        ignoreUnknown=true)

public class RestApiException extends Exception {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private int code;
    private String error;
    private String error_description;

    public RestApiException(int code, String error){
        this.code = code;
        this.error = error;
        this.error_description = null;
    }


    public RestApiException(int code, String error, String reason){
        this.code = code;
        this.error = error;
        this.error_description = reason;
    }


    public RestApiException(BindingResult result, String message) {


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
        this.error = message;
        this.error_description = "Received wrong value(s) : "+buffer.toString()+" ;";
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

    public String getReason() {
        return error_description;
    }

    public void setReason(String reason) {
        this.error_description = reason;
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


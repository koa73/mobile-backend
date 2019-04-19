package ru.mobile.lib.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@JsonIgnoreProperties(value =
        {"cause", "stackTrace", "localizedMessage", "suppressed", "code", "message", "status", "causeReason"},
        ignoreUnknown=true)

public class RestApiException extends Exception {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    private int status;
    private String error;
    private String reason;


    public RestApiException(int resultCode, String errMsg){
        this.status = resultCode;
        this.error = errMsg;
        this.reason = null;
    }


    public RestApiException(int resultCode, String errMsg, String causeReason){
        this.status = resultCode;
        this.error = errMsg;
        this.reason = causeReason;
    }


    public RestApiException(BindingResult result) {

        this.status = 101; // Bad JSON request values or format
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


    public int getResultCode() {
        return status;
    }

    public void setResultCode(int resultCode) {
        this.status = resultCode;
    }

    public String getErrMsg() {
        return error;
    }

    public void setErrMsg(String errMsg) {
        this.error = errMsg;
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
                "\"status\":\""+status+
                "\", \"error\":\""+error +
                "\", \"reason\":\""+reason +
                "\"}";
    }

}


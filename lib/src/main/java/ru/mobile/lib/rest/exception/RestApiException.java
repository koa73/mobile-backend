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

    private int resultCode;
    private String errMsg;
    private String reason;


    public RestApiException(int resultCode, String errMsg){
        this.resultCode = resultCode;
        this.errMsg = errMsg;
        this.reason = null;
    }


    public RestApiException(int resultCode, String errMsg, String causeReason){
        this.resultCode = resultCode;
        this.errMsg = errMsg;
        this.reason = causeReason;
    }


    public RestApiException(BindingResult result) {

        this.resultCode = 101; // Bad JSON request values or format
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
        this.errMsg="Received wrong value(s) : "+buffer.toString()+" ;";
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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
                "\"status\":\""+resultCode+
                "\", \"message\":\""+errMsg +
                "\", \"cause\":\""+reason +
                "\"}";
    }

}


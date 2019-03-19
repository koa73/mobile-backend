package ru.mobile.lib.rest.exceptions;

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

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HttpStatus status;

    private int cmdCode;

    private int resultCode;

    private String errMsg;

    private String causeReason;

    public RestApiException(int cmdCode, int resultCode, String errMsg ) {
        this.status= HttpStatus.BAD_REQUEST;
        this.cmdCode = cmdCode;
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public RestApiException(HttpStatus status, int cmdCode, int resultCode, String errMsg ) {
        this.status=status;
        this.cmdCode = cmdCode;
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public RestApiException(HttpStatus status, int resultCode, String errMsg ) {
        this.status=status;
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }


    public RestApiException(int resultCode, String errMsg ) {
        this.cmdCode = 0;
        this.status= HttpStatus.BAD_REQUEST;
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public RestApiException(BindingResult result, int cmdCode) {

        this.status= HttpStatus.BAD_REQUEST;
        this.cmdCode = cmdCode;
        this.resultCode = 102;

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


    public HttpStatus getStatus() {
        return status;
    }

    public int getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(int cmdCode) {
        this.cmdCode = cmdCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getCauseReason() {
        return causeReason;
    }

    public void setCauseReason(String causeReason) {
        this.causeReason = causeReason;
    }

    @Override
    public String toString() {
        return "{"+
                "\"cmdCode\":\""+cmdCode+
                "\", resultCode\":\""+resultCode+
                "\", \"error\":\""+errMsg+
                "\"}";
    }

}


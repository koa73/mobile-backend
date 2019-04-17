package ru.mobile.lib.rest.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * Created by OAKutsenko on 12.10.2017.
 */

public class WebApiException extends Exception {

    @JsonIgnoreProperties(value =
            {"cause", "stackTrace", "localizedMessage", "suppressed", "message", "status"},
            ignoreUnknown=true)


    private int resultCode;

    private String errMsg;

    private String reason;


    public WebApiException(){}

    public WebApiException(int resultCode, String errMsg){
        this.resultCode = resultCode;
        this.errMsg = errMsg;
        this.reason = null;
    }


    public WebApiException(int resultCode, String errMsg, String causeReason){
        this.resultCode = resultCode;
        this.errMsg = errMsg;
        this.reason = causeReason;
    }

    public String getCauseReason() {
        return reason;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setReason(String causeReason) {
        this.reason = causeReason;
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

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

    public WebApiException (int resultCode, String errMsg){
        this.code = resultCode;
        this.error = errMsg;
        this.error_description = null;
    }


    public WebApiException (int resultCode, String errMsg, String causeReason){
        this.code = resultCode;
        this.error = errMsg;
        this.error_description = causeReason;
    }

    public String getCauseReason() {
        return error_description;
    }

    public String getErrMsg() {
        return error;
    }

    public void setErrMsg(String errMsg) {
        this.error = errMsg;
    }

    public int getResultCode() {
        return code;
    }

    public void setReason(String causeReason) {
        this.error_description = causeReason;
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

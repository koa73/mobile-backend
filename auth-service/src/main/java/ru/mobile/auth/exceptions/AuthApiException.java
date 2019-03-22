package ru.mobile.auth.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;


@JsonIgnoreProperties(value={"cause", "stackTrace", "localizedMessage", "suppressed", "code", "message", "status"},
        ignoreUnknown=true)
public class AuthApiException extends Exception {

    private final HttpStatus status;


    private int resultCode;

    private String errMsg;


    public AuthApiException(int resultCode, String errMsg ) {
        this.status = HttpStatus.BAD_REQUEST;
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public AuthApiException(HttpStatus status, int resultCode, String errMsg) {
        this.status = status;
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String toString() {
        return "AuthApiException{"+
                "resultCode='"+resultCode+'\''+
                ", error-description="+errMsg+
                '}';
    }
}


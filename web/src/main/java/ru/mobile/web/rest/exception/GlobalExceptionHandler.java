package ru.mobile.web.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.mobile.web.config.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * Created by OAKutsenko on 09.10.2017.
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    Messages messages;

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(MethodArgumentNotValidException exception) {

        return error(exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()));
    }

    @ExceptionHandler
    @ResponseBody
    public String webHandler(HttpServletResponse httpServletResponse, WebApiException exception) {

        exception.setReason(exception.getStackTrace()[0].getClassName()+"@"+exception.getStackTrace()[0].getMethodName());

        if (exception.getResultCode() == 100){

            if (exception.getErrMsg() != null) {

                httpServletResponse.setStatus(400);
                exception.setErrMsg(messages.get("error" + getReceivedErrorMsg(exception.getErrMsg())));

            } else {

                httpServletResponse.setStatus(500);
                exception.setErrMsg(messages.get("error.400"));
            }

        } else {

            httpServletResponse.setStatus(exception.getResultCode());
        }

        return exception.toString();
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden(final HttpServletRequest request, final Throwable throwable, final Model model){
        return "error";
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exception(final Throwable throwable, final Model model){

        String errorMessage = ".unavailable";

        if (throwable instanceof ConstraintViolationException){
            errorMessage = ".request";
        }


        log.error("----------------------------->>>>" + throwable.getCause()+" , " +  throwable.getMessage());
        model.addAttribute("prefix", errorMessage);
        return "error";
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }

    private String getReceivedErrorMsg(String error){

        final String errCode = error.replaceAll(".*\n*.*[\"resultCode\":|errCode:]\\s?(\\d{3}).*", "$1");
        String result = "";

        switch (errCode){

            default :
                result = ".400"; // unknown error
                break;
        }
        return result;
    }
}

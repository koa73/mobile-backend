package ru.mobile.lib.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@ControllerAdvice
public class GlobalControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());


	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<RestApiException> handleException(RestApiException exception, HttpServletRequest request){
		return new ResponseEntity<RestApiException>(exception, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {

		log.error(ex.getClass().getCanonicalName());

		RestApiException exception = new RestApiException(100, "Everything is BAD.", ex.getCause().toString());
		return new ResponseEntity<Object>(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}


	@InitBinder
	public void dataBinding(WebDataBinder binder, HttpServletRequest request) {

		if (request.isUserInRole("ROLE_USER")){

			binder.bind(new MutablePropertyValues(Collections.singletonMap(
					"user", request.getUserPrincipal().getName())));

			binder.bind(new MutablePropertyValues(Collections.singletonMap(
					"phone", request.getAttribute("phone"))));
		}
	}
}
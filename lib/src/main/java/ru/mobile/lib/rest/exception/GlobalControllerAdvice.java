package ru.mobile.lib.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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


	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<RestApiException> handleException(DataIntegrityViolationException e, HttpServletRequest req) {

		int resultCode = 103; // unknown SQL req error
		String errMsg = "SQL request error.";

		final String errorCode = e.getCause().getLocalizedMessage().replaceAll(".*errCode:\\s(\\d{3})(.*\n.*)*", "$1");
		if (errorCode.length() == 3) {
			resultCode = Integer.parseInt(errorCode);
			errMsg = e.getCause().getLocalizedMessage().replaceAll(".*:\\s(.*)\\serrCode(.*\n.*)*", "$1");
		}

		RestApiException exception = new RestApiException(resultCode, errMsg, "SQL request error.");
		return new ResponseEntity<RestApiException>(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<RestApiException> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		RestApiException exception = new RestApiException(104, "Access denied.");
		return new ResponseEntity<RestApiException>(exception, new HttpHeaders(), HttpStatus.FORBIDDEN);
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
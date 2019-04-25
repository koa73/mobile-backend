package ru.mobile.auth.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(AuthApiException.class)
	public ResponseEntity<AuthApiException> handleException(AuthApiException exception, HttpServletRequest req) {
		return new ResponseEntity<AuthApiException>(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<AuthApiException> handleException(DataIntegrityViolationException e, HttpServletRequest req) {

		final String errorCode = e.getCause().getMessage().replaceAll("^.*errCode:\\s(\\d{3})\n?.*", "$1");
		final int code = (errorCode.length()== 3)?Integer.parseInt(errorCode):103; // unknown SQL req error

		AuthApiException exception=new AuthApiException( code, "SQL request error.");
		return handleException(exception, req);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<AuthApiException> handleException(Throwable throwable, HttpServletRequest req) {
		AuthApiException exception=new AuthApiException( 100,
				"The operation was aborted.");

		return handleException(exception, req);
	}
}
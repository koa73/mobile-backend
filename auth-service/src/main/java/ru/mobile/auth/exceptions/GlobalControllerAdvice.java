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
		log.error("Sending error to client ( "+req.getUserPrincipal().getName()+" ) \"{}\"", exception.getErrMsg());
		return new ResponseEntity<AuthApiException>(exception, exception.getStatus());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<AuthApiException> handleException(DataIntegrityViolationException e, HttpServletRequest req) {

		final String errorCode = e.getCause().getMessage().replaceAll("^.*errCode:\\s(\\d{3})\n?.*", "$1");
		final int resultCode = (errorCode.length()== 3)?Integer.parseInt(errorCode):101; // unknown SQL req error

		AuthApiException exception=new AuthApiException(HttpStatus.INTERNAL_SERVER_ERROR, resultCode,
				"SQL request error.");
		return handleException(exception, req);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<AuthApiException> handleException(Throwable throwable, HttpServletRequest req) {
		AuthApiException exception=new AuthApiException(HttpStatus.INTERNAL_SERVER_ERROR, 107,
				"The operation was aborted.");

		return handleException(exception, req);
	}
}
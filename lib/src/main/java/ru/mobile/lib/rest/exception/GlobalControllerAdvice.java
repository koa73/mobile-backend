package ru.mobile.lib.rest.exception;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@ControllerAdvice
public class GlobalControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<RestApiException> handleException(RestApiException exception, HttpServletRequest request) {

		if (exception.getCmdCode() == 0)
			exception.setCmdCode(Integer.parseInt(request.getRequestURI().replaceAll(".*/(\\d{4}).*?","$1"))+1000);

		String errorReason = "";
		if (exception.getCauseReason() != null)
			errorReason = exception.getCauseReason();

		log.error("Sending error to client ( " + request.getAttribute("phone")+" ) \"{}\" " +
				errorReason, exception.getErrMsg());

		return new ResponseEntity<RestApiException>(exception, exception.getStatus());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RestApiException> handleException(HttpMessageNotReadableException e, HttpServletRequest req){
		RestApiException exception = new RestApiException(100, "Wrong json request format.");
		log.error(e.getLocalizedMessage());
		return handleException(exception, req);
	}

	@ExceptionHandler(HystrixRuntimeException.class)
	public ResponseEntity<RestApiException> handleException(HystrixRuntimeException e, HttpServletRequest req) {
		int resultCode = 100;
		String errMsg = e.getMessage();

		if (e.getCause() instanceof FeignException) {

			resultCode = Integer.valueOf(e.getCause().getMessage().replaceAll(".*\n.*resultCode\":(\\d{3}).*}", "$1"));
			errMsg = e.getCause().getMessage().replaceAll(".*\n.*errMsg\":\"(.*)\"}", "$1");
		}
		RestApiException exception=new RestApiException(resultCode, errMsg);
		return handleException(exception, req);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<RestApiException> handleException(DataIntegrityViolationException e, HttpServletRequest req) {

		int resultCode = 101; // unknown SQL req error
		String errMsg = "SQL request error.";

		final String errorCode = e.getCause().getLocalizedMessage().replaceAll(".*errCode:\\s(\\d{3})(.*\n.*)*", "$1");
		if (errorCode.length() == 3) {
			resultCode = Integer.parseInt(errorCode);
			errMsg = e.getCause().getLocalizedMessage().replaceAll(".*:\\s(.*)\\serrCode(.*\n.*)*", "$1");
		}

		RestApiException exception=new RestApiException(
				resultCode,
				errMsg);
		exception.setCauseReason(" ("+e.getCause().getLocalizedMessage()+")");
		return handleException(exception, req);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<RestApiException> handleException(Throwable throwable, HttpServletRequest req) {

		log.error("----------------------------------------------------------------------------\n");
		final int cmdCode = Integer.parseInt(req.getRequestURI().replaceAll(".*/(\\d{4}).*?","$1"))+1000;

		log.error("Cause : "+throwable.getCause()+ throwable.getMessage());
		String causeMessage = "";
		if (throwable.getCause() != null)
			causeMessage = " " + throwable.getCause().getMessage();

		RestApiException exception=new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR, cmdCode, 100,
				throwable.getMessage()  + causeMessage);
		return handleException(exception, req);
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
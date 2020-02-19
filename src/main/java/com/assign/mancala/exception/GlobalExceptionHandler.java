package com.assign.mancala.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assign.mancala.controller.GameController;
import com.assign.mancala.controller.PlayController;
import com.assign.mancala.controller.PlayerController;
import com.assign.mancala.object.GlobalErrorResponse;

/**
 * 
 * Class to handle the exceptions
 *
 */
@ControllerAdvice(assignableTypes = { GameController.class, PlayController.class, PlayerController.class })
public class GlobalExceptionHandler {

	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler
	public ResponseEntity<GlobalErrorResponse> onHttpMessageNotReadable(final HttpMessageNotReadableException e)
			throws Throwable {
		return createBadRequest();
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public ResponseEntity passThroughHttpMessageConversionException(HttpMessageConversionException ex) {
		return createBadRequest();
	}

	private ResponseEntity<GlobalErrorResponse> createBadRequest() {
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
		HttpHeaders headers = getJsonHttpHeaders();
		return new ResponseEntity<>(
				new GlobalErrorResponse(responseStatus.toString(), responseStatus.getReasonPhrase()), headers,
				responseStatus);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<GlobalErrorResponse> handleException(Exception exception) {
		HttpHeaders headers = getJsonHttpHeaders();
		LOGGER.error("Global exception caught.", exception);
		return new ResponseEntity<>(new GlobalErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected HttpHeaders getJsonHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
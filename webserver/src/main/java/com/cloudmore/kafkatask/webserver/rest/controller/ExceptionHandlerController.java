package com.cloudmore.kafkatask.webserver.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.ServletException;

@ControllerAdvice
public class ExceptionHandlerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

	private static ResponseEntity<ErrorResponse> constructResponse(final HttpStatus httpStatus,
																   final String description) {
		return new ResponseEntity<>(new ErrorResponse(httpStatus.value(), description), httpStatus);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleException500(final Throwable throwable) {
		LOGGER.error("Parsing internal server error / 500", throwable);

		return constructResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
	}

	@ExceptionHandler({ServletException.class, MethodArgumentNotValidException.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleException400(final Throwable throwable) {
		LOGGER.error("Parsing bad request / 400", throwable);

		return constructResponse(HttpStatus.BAD_REQUEST, throwable.getMessage());
	}

	@ExceptionHandler({MethodNotAllowedException.class, HttpRequestMethodNotSupportedException.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleException404(final Throwable throwable) {
		LOGGER.error("Parsing not found", throwable);

		return constructResponse(HttpStatus.NOT_FOUND, throwable.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final Throwable throwable) {
		LOGGER.error("Parsing JSON input exception", throwable);

		final Throwable cause = throwable.getCause();
		return (cause instanceof JsonProcessingException || cause instanceof RuntimeJsonMappingException)
			? handleException400(cause)
			: handleException500(throwable);
	}

	@Value
	@ApiModel
	public static class ErrorResponse {
		@ApiModelProperty
		int status;
		@ApiModelProperty
		String message;
	}
}

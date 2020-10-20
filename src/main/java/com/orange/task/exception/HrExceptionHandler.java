package com.orange.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class HrExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ForbiddenOperationException.class)
	public ResponseEntity<String> handleForbiddenOperationException(RuntimeException e) {
		return response(HttpStatus.FORBIDDEN, e);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<String> handleRecordNotFoundException(RuntimeException e) {
		return response(HttpStatus.NOT_FOUND, e);
	}

	@ExceptionHandler(MissingDataException.class)
	public ResponseEntity<String> handleMissingDataException(RuntimeException e) {
		return response(HttpStatus.BAD_REQUEST, e);
	}

	private ResponseEntity<String> response(HttpStatus status, Exception e) {
		log.error("Exception: {}", e);
		return ResponseEntity.status(status).body(e.getMessage());
	}
}

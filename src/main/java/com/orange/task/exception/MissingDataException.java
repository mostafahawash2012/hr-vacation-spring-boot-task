package com.orange.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingDataException(String message) {
		super(message);
	}
}

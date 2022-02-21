package com.sintad.exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "NotFoundException not found")
public class NotFoundExpcetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundExpcetion(String message, Throwable cause) {
		super(message, cause);

	}

	public NotFoundExpcetion(String message) {
		super(message);

	}




}

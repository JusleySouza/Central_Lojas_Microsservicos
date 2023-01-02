package com.central.stores.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DuplicateDocumentsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateDocumentsException(String exception) {
		super(exception);
	}
}
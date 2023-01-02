package com.central.stores.employees.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

@Getter
public class ExceptionResponse implements Serializable {
	
	private static final long serialVersionUID = 1l;
	
	protected Date timestamp;
	protected String message;
	protected String details;
	
	public ExceptionResponse() {
	}

	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

}
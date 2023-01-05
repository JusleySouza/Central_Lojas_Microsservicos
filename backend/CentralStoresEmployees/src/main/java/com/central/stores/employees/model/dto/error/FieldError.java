package com.central.stores.employees.model.dto.error;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Generated
public class FieldError {

	private String field;
	private String message;
	
	public FieldError(String field, String message) {
		this.field = field;
		this.message = message;
	}
}

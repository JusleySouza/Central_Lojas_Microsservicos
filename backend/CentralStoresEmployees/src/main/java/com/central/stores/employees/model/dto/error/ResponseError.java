package com.central.stores.employees.model.dto.error;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Generated
public class ResponseError {

	private final String MESSAGE = "Validation error";
	private Collection<FieldError> errors;

	public ResponseError(Collection<FieldError> errors) {
		this.errors = errors;
	}

	public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {

		List<FieldError> errors = violations
			.stream()
			.map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
			.collect(Collectors.toList());
		
		ResponseError responseError = new ResponseError(errors);
		
		return responseError;
	}
}
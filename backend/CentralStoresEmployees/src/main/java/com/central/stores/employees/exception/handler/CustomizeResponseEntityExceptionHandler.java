package com.central.stores.employees.exception.handler;

import java.util.Date;

import com.central.stores.employees.exception.DuplicateDocumentsException;
import com.central.stores.employees.exception.ExceptionResponse;
import com.central.stores.employees.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Generated;

@Generated
@RestController
@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), 
				exception.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception exception, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), 
				exception.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateDocumentsException.class)
	public final ResponseEntity<ExceptionResponse> handleDuplicateDocumentsExceptions(Exception exception, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), 
				exception.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}

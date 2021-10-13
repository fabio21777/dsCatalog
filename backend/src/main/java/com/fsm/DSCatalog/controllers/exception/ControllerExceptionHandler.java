package com.fsm.DSCatalog.controllers.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(ControllerNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException e,HttpServletRequest request){
		HttpStatus 	status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Entity not found");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBaseException(DataBaseException e,HttpServletRequest request){
		HttpStatus 	status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setMessage(e.getMessage());
		error.setError("DataBaseException");
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> notvalid(MethodArgumentNotValidException e,HttpServletRequest request){
		HttpStatus 	status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("erro validation");
		error.setPath(request.getRequestURI());
		for(FieldError fieldMessage : e.getBindingResult().getFieldErrors() ) {
			error.addError(fieldMessage.getField(),fieldMessage.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(error);
	}
}

package com.fsm.DSCatalog.services.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fsm.DSCatalog.controllers.exception.StandardError;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(ControllerNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException e,HttpServletRequest request){
		HttpStatus 	status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setMessage(e.getMessage());
		error.setPath("Entity not found");
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
		error.setPath("Entity not found");
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
}

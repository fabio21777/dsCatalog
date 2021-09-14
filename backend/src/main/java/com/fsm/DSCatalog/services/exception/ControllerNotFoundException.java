package com.fsm.DSCatalog.services.exception;

public class ControllerNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ControllerNotFoundException(String msg) {
		super(msg);
	}

}

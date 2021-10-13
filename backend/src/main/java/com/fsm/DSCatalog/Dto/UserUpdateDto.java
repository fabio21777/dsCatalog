package com.fsm.DSCatalog.Dto;

import com.fsm.DSCatalog.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDto  extends UserDto{
	private static final long serialVersionUID = 1L;
	UserUpdateDto(){
		super();
	}
}

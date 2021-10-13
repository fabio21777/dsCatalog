package com.fsm.DSCatalog.Dto;

import com.fsm.DSCatalog.services.validation.UserInsertValid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@UserInsertValid
public class UserInsertDto  extends UserDto{
	private static final long serialVersionUID = 1L;
	private String password;

	UserInsertDto(){
		super();
	}
}

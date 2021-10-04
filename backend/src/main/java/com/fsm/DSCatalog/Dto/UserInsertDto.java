package com.fsm.DSCatalog.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDto  extends UserDto{
	private static final long serialVersionUID = 1L;
	private String password;
}

package com.fsm.DSCatalog.controllers.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class FieldMessage  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String message;

}

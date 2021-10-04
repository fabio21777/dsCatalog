package com.fsm.DSCatalog.entities;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
	@Include
	private Long id;
	private String fistName;
	private String lastName;
	private String email;
	private String password;
	private Set<Role>roles = new HashSet<>();
 	
}

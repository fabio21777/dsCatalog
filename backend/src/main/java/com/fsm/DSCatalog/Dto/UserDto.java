package com.fsm.DSCatalog.Dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fsm.DSCatalog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Set<RoleDto>roles = new HashSet<>();
	
	public UserDto (User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		user.getRoles().forEach(role -> roles.add(new RoleDto(role)));
	}
}

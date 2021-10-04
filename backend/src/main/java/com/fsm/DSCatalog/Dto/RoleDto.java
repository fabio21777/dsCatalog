package com.fsm.DSCatalog.Dto;

import java.io.Serializable;

import com.fsm.DSCatalog.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String authority;
	
	public RoleDto(Role role) {
		this.id = role.getId();
		this.authority = role.getAuthority();
	}
}

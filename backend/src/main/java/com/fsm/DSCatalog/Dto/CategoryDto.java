package com.fsm.DSCatalog.Dto;

import java.io.Serializable;

import com.fsm.DSCatalog.entities.Category;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoryDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long  id;
	private String name;
	
	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
}

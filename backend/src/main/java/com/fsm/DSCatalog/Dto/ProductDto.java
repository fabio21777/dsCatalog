package com.fsm.DSCatalog.Dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fsm.DSCatalog.entities.Category;
import com.fsm.DSCatalog.entities.Product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDto implements Serializable{
	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	private Long id;
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "campo Requerido")
	private String name;
	@NotBlank(message = "campo Requerido")
	private String description;
	@Positive(message = "o preço deve ter um valor positivo")
	private Double price;
	
	private String imgUrl;
	@PastOrPresent(message = "A data do produto não pode ser futura")
	private Instant date;
	@NotEmpty(message = "um produto não pode ser inserido sem categoria")
	private List<CategoryDto> categories = new ArrayList<>();
	
	
	public ProductDto(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
	}
	public ProductDto(Product entity, Set<Category> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDto(cat)));
	}
	
}

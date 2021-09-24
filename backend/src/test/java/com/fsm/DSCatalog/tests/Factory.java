package com.fsm.DSCatalog.tests;

import java.time.Instant;

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.entities.Category;
import com.fsm.DSCatalog.entities.Product;

public class Factory {
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, "Electronics"));
		return product;		
	}
	
	public static ProductDto createProductDTO() {
		Product product = createProduct();
		return new ProductDto(product, product.getCategories());
	}
}

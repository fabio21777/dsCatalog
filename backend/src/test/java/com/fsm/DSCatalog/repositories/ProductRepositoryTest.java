package com.fsm.DSCatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fsm.DSCatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {
	@Autowired
	ProductRepository productRepository;
	@Test
	public void deleteShouldDeleteObjectwhenIdexists() {
		Long id = 1L;
		productRepository.deleteById(id);
		
		Optional<Product> result = productRepository.findById(id);
		Assertions.assertTrue(result.isEmpty());
	}
}

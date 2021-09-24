package com.fsm.DSCatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.fsm.DSCatalog.entities.Product;
import com.fsm.DSCatalog.tests.Factory;


@DataJpaTest
public class ProductRepositoryTest {
	private Long existingID;
	private Long noExistingID;
	private Long countTotalProducts;
	
	
	@Autowired
	ProductRepository productRepository;
	
	@BeforeEach
	public void setUp() throws Exception{
		existingID = 1L;
		noExistingID = 9999999999999L;
		countTotalProducts = 25L;
	}
	
	
	@Test
	public void deleteShouldDeleteObjectWhenIdexists() {
		
		productRepository.deleteById(existingID);
		
		Optional<Product> result = productRepository.findById(existingID);
		Assertions.assertTrue(result.isEmpty());
	}
	@Test
	public void deleteShouldeEmptyResultDataAccessExceptionWhenDoesNotExist() {
		Assertions.assertThrows(EmptyResultDataAccessException.class,() -> 
			{
				productRepository.deleteById(noExistingID);
			}

		);
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		productRepository.save(product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1,product.getId() );
	}
	
	@Test 
	public void findByIdSholdFindObjectOptionalNotVoidWhenIdExists() {
		Optional<Product> product = productRepository.findById(existingID);
		Assertions.assertTrue(product.isPresent());
	}
	@Test 
	public void findByIdSholdFindObjectOptionalIsVoidWhenDoesNotExistId() {
		Optional<Product> product = productRepository.findById(noExistingID);
		Assertions.assertTrue(product.isEmpty());
	}
	
	
}

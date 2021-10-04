package com.fsm.DSCatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.repositories.ProductRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;
	
	private long idExists;
	private long nonIdExists;
	private long countTotalProducts;
	
	@BeforeEach
	public void setUp() throws Exception{
		idExists = 1L;
		nonIdExists = 1000L;
		countTotalProducts = 25L;
	}
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		
		productService.delete(idExists);
		Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ControllerNotFoundException.class, () -> {
			productService.delete(nonIdExists);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<ProductDto> page =  productService.findAll(pageRequest);
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals(0, page.getNumber());
		Assertions.assertEquals(10, page.getSize());
		Assertions.assertEquals(countTotalProducts, page.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		PageRequest pageRequest = PageRequest.of(50, 10);
		Page<ProductDto> page =  productService.findAll(pageRequest);
		Assertions.assertTrue(page.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		Page<ProductDto> page =  productService.findAll(pageRequest);
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals("Macbook Pro",page.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer",page.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa",page.getContent().get(2).getName());
		
	}
	
}

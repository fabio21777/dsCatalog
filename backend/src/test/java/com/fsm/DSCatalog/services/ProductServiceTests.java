package com.fsm.DSCatalog.services;

import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.entities.Category;
import com.fsm.DSCatalog.entities.Product;
import com.fsm.DSCatalog.repositories.CategoryRepository;
import com.fsm.DSCatalog.repositories.ProductRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;
import com.fsm.DSCatalog.tests.Factory;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private Long integrityViolation;
	private Long existingID;
	private Long nonExistingID;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDto prodDto;
	@BeforeEach
	void setUp() throws Exception{
		existingID = 1L;
		nonExistingID = 999999999999L;
		integrityViolation = 4L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		prodDto = Factory.createProductDTO();
		
		page = new PageImpl<>(List.of(product));
		
		Mockito.doNothing().when(productRepository).deleteById(existingID);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingID);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(integrityViolation);
		
		Mockito.when(productRepository.getOne(existingID)).thenReturn(product);
		Mockito.when(productRepository.getOne(nonExistingID)).thenThrow(ControllerNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(existingID)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistingID)).thenThrow(ControllerNotFoundException.class);
		
		Mockito.when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(productRepository.findById(existingID)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingID)).thenReturn(Optional.empty());
		
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
	}
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(()->{
			productService.delete(existingID);
		});
		Mockito.verify(productRepository, times(1)).deleteById(existingID);;
		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdControllerNotFoundExceptionNonExists() {
		Assertions.assertThrows(ControllerNotFoundException.class, () -> {
			productService.delete(nonExistingID);
		}
		);
		Mockito.verify(productRepository,times(1)).deleteById(nonExistingID);
	}
	@Test
	public void deleteShouldDataBaseExceptionDoNothingWhenIDintegrityViolation() {
		Assertions.assertThrows(DataBaseException.class, () -> {
			productService.delete(integrityViolation);
		}
		);
		Mockito.verify(productRepository,times(1)).deleteById(integrityViolation);
	}
	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 12);
		Page<ProductDto> pages = productService.findAll(pageable);
		Assertions.assertFalse(pages.isEmpty());
		Mockito.verify(productRepository,times(1)).findAll(pageable);
	}
	@Test 
	public void findByidShouldReturnProductDto() {
		prodDto =  productService.findById(existingID);
		Assertions.assertNotNull(prodDto);
	}
	
	@Test
	public void findByidShouldReturnProductDtoInvalidId() {
		Assertions.assertThrows(ControllerNotFoundException.class,() -> {
			productService.findById(nonExistingID);
		});
		
		Mockito.verify(productRepository,times(1)).findById(nonExistingID);
	}
	
	@Test 
	public void updateByidShouldReturnProductDto() {
		ProductDto prod =  productService.update(existingID,prodDto);
		Assertions.assertNotNull(prod);
		Mockito.verify(productRepository,times(1)).getOne(existingID);
		Mockito.verify(categoryRepository,times(1)).getOne(existingID);
	}
	
	@Test
	public void updateByidShouldReturnExceptionsIdInvalid() {
		Assertions.assertThrows(ControllerNotFoundException.class,() -> {
			productService.update(nonExistingID, prodDto);
		});
		Mockito.verify(productRepository,times(1)).getOne(nonExistingID);
	}
	
	
}

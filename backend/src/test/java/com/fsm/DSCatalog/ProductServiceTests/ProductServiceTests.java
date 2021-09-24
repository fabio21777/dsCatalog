package com.fsm.DSCatalog.ProductServiceTests;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fsm.DSCatalog.repositories.ProductRepository;
import com.fsm.DSCatalog.services.ProductService;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService productService;
	@Mock
	private ProductRepository productRepository;
	
	
	private Long existingID;
	private Long nonExistingID;

	@BeforeEach
	void setUp() throws Exception{
		existingID = 1L;
		nonExistingID = 999999999999L;
		
		Mockito.doNothing().when(productRepository).deleteById(existingID);
		Mockito.doThrow(ControllerNotFoundException.class).when(productRepository).deleteById(nonExistingID);
		
	}
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(()->{
			productService.delete(existingID);
		});
		Mockito.verify(productRepository, times(1)).deleteById(existingID);;
		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdNonExists() {
		Assertions.assertThrows(ControllerNotFoundException.class, () -> {
			productService.delete(nonExistingID);
		}
		);
		Mockito.verify(productRepository,times(1)).deleteById(nonExistingID);
	}
}

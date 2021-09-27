package com.fsm.DSCatalog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.services.ProductService;
import com.fsm.DSCatalog.tests.Factory;

@WebMvcTest(ProductControlle.class)
public class ProductController {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	private ProductDto productDto;
	private PageImpl<ProductDto> page;
	
	@BeforeEach
	void setUp() throws Exception {
		productDto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDto));
		Mockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(page);

	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		ResultActions resultActions = mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON)
				);
		
		
		resultActions.andExpect(status().isOk());
	}
	
}

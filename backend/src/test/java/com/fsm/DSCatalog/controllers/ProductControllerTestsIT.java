package com.fsm.DSCatalog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTestsIT {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
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
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
				.accept(MediaType.APPLICATION_JSON));
		
			resultActions.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
			resultActions.andExpect(jsonPath("$.content").exists());
			resultActions.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
			resultActions.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
			resultActions.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
		
	}
	
	@Test
	public void updateShouldReturnProductDtoWhenIdExists() throws Exception {
		
		ProductDto productDTO = Factory.createProductDTO();
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		String expectedName = productDTO.getName();
		String expectedDescription = productDTO.getDescription();
		
		ResultActions result = 
				mockMvc.perform(put("/products/{id}", idExists)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(productDTO.getId()));
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.description").value(expectedDescription));
	}
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist()throws Exception {
		
		ProductDto productDTO = Factory.createProductDTO();
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		

		ResultActions result = 
				mockMvc.perform(put("/products/{id}", nonIdExists)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
}

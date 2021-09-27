package com.fsm.DSCatalog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.services.ProductService;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;
import com.fsm.DSCatalog.tests.Factory;

@WebMvcTest(ProductControlle.class)
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService service;
	
	
	
	private ProductDto productDto;
	private PageImpl<ProductDto> page;
	private Long existId;
	private Long nonexistId;
	private Long integrityViolationId;
	
	@BeforeEach
	void setUp() throws Exception {
		productDto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDto));
		existId = 1L;
		nonexistId = 999999999L;
		integrityViolationId = 2L;
		
		Mockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(service.findById(existId)).thenReturn(productDto);
		Mockito.when(service.findById(nonexistId)).thenThrow(ControllerNotFoundException.class);
		
		
		Mockito.when(service.update(ArgumentMatchers.eq(existId), ArgumentMatchers.any())).thenReturn(productDto);
		Mockito.when(service.update(ArgumentMatchers.eq(nonexistId),ArgumentMatchers.any())).thenThrow(ControllerNotFoundException.class);
		
		Mockito.doNothing().when(service).delete(existId);
		Mockito.doThrow(ControllerNotFoundException.class).when(service).delete(nonexistId);
		Mockito.doThrow(DataBaseException.class).when(service).delete(integrityViolationId);
		
		Mockito.when(service.insert(ArgumentMatchers.any())).thenReturn(productDto);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		ResultActions resultActions = mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON)
				);
		
		
		resultActions.andExpect(status().isOk());
	}
	
	
	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception {
		
		ResultActions resultActions = 
				mockMvc.perform(get("/products/{id}", existId)
					.accept(MediaType.APPLICATION_JSON));
		
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(jsonPath("$.id").exists());
		resultActions.andExpect(jsonPath("$.name").exists());
		resultActions.andExpect(jsonPath("$.description").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotfoundwhenNoexistingId() throws Exception{
		ResultActions resultActions = mockMvc.perform(get("/products/{id}",nonexistId)
				.accept(MediaType.APPLICATION_JSON));
		
		
		resultActions.andExpect(status().isNotFound());

	}
	
	
	@Test
	public void updateShouldReturnProductDtoWhenIdExists() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDto);
		ResultActions resultActions = mockMvc.perform(put("/products/{id}",existId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(jsonPath("$.id").exists());
		resultActions.andExpect(jsonPath("$.name").exists());
		resultActions.andExpect(jsonPath("$.description").exists());
	}
	
	@Test
	public void updateShouldReturnProductDtoWhenIDDoesNotExists() throws Exception{
		String jsonBody = objectMapper.writeValueAsString(productDto);
		ResultActions resultActions = mockMvc.perform(get("/products/{id}",nonexistId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		resultActions.andExpect(status().isNotFound());
	}
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		ResultActions resultActions = mockMvc.perform(delete("/products/{id}",existId));
		resultActions.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIDDoesNotExists() throws Exception {
		ResultActions resultActions = mockMvc.perform(delete("/products/{id}",nonexistId));
		resultActions.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnProductDtoAndcreated() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDto);
		ResultActions resultActions = mockMvc.perform(post("/products")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		resultActions.andExpect(status().isCreated());
		resultActions.andExpect(jsonPath("$.id").exists());
		resultActions.andExpect(jsonPath("$.name").exists());
		resultActions.andExpect(jsonPath("$.description").exists());
	}
	
	
	
}

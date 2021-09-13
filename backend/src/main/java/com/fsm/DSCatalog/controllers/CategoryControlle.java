package com.fsm.DSCatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsm.DSCatalog.Dto.CategoryDto;
import com.fsm.DSCatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryControlle {
	@Autowired
	private CategoryService categoryService;
	@GetMapping
	public ResponseEntity<List<CategoryDto>> findAll(){
		List<CategoryDto>list = categoryService.findAll();
		return ResponseEntity.ok(list);
	}
	
}

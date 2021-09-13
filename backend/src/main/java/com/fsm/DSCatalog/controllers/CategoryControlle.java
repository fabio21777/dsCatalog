package com.fsm.DSCatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsm.DSCatalog.entities.Category;
import com.fsm.DSCatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryControlle {
	@Autowired
	private CategoryService categoryService;
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		List<Category>list = categoryService.findAll();
		return ResponseEntity.ok(list);
	}
	
}

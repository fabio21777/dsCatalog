package com.fsm.DSCatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsm.DSCatalog.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryControlle {
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L,"Books" ));
		list.add(new Category(2L,"Electonics" ));
		return ResponseEntity.ok(list);
	}
	
}

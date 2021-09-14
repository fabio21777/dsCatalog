package com.fsm.DSCatalog.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id){
		CategoryDto dto = categoryService.findById(id);
		return ResponseEntity.ok(dto);
	}
	@PostMapping
	public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto dto){
		dto = categoryService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDto>update(@PathVariable Long id, @RequestBody CategoryDto dto){
		dto = categoryService.update(id,dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

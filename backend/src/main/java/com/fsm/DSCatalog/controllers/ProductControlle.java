package com.fsm.DSCatalog.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductControlle {
	@Autowired
	private ProductService productService;
	@GetMapping
	public ResponseEntity<Page<ProductDto>> findAll (Pageable pageable){
		Page<ProductDto> pages = productService.findAll(pageable);
		return ResponseEntity.ok(pages);
		
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id){
		ProductDto dto = productService.findById(id);
		return ResponseEntity.ok(dto);
	}
	@PostMapping
	public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto){
		dto = productService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDto>update(@PathVariable Long id, @Valid @RequestBody ProductDto dto){
		dto = productService.update(id,dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

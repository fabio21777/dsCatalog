package com.fsm.DSCatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductControlle {
	@Autowired
	private ProductService productService;
	@GetMapping
	public ResponseEntity<Page<ProductDto>> findAll (
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction){
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		Page<ProductDto> pages = productService.findAll(pageRequest);
		return ResponseEntity.ok(pages);
		
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id){
		ProductDto dto = productService.findById(id);
		return ResponseEntity.ok(dto);
	}
	/*@PostMapping
	public ResponseEntity<ProductDto> insert(@RequestBody ProductDto dto){
		dto = productService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDto>update(@PathVariable Long id, @RequestBody ProductDto dto){
		dto = productService.update(id,dto);
		return ResponseEntity.ok(dto);
	}*/
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

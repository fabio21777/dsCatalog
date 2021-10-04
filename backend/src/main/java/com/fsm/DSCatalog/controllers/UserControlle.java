package com.fsm.DSCatalog.controllers;

import java.net.URI;

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

import com.fsm.DSCatalog.Dto.UserDto;
import com.fsm.DSCatalog.Dto.UserInsertDto;
import com.fsm.DSCatalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserControlle {
	@Autowired
	private UserService userService;
	@GetMapping
	public ResponseEntity<Page<UserDto>> findAll (Pageable pageable){
		Page<UserDto> pages = userService.findAll(pageable);
		return ResponseEntity.ok(pages);
		
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id){
		UserDto dto = userService.findById(id);
		return ResponseEntity.ok(dto);
	}
	@PostMapping
	public ResponseEntity<UserDto> insert(@RequestBody UserInsertDto dto){
		UserDto  newdto = userService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newdto.getId()).toUri();
		return ResponseEntity.created(uri).body(newdto);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDto>update(@PathVariable Long id, @RequestBody UserDto dto){
		dto = userService.update(id,dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

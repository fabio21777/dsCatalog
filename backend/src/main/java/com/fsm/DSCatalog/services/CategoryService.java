package com.fsm.DSCatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.DSCatalog.Dto.CategoryDto;
import com.fsm.DSCatalog.entities.Category;
import com.fsm.DSCatalog.repositories.CategoryRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Transactional(readOnly = true)
	public List<CategoryDto> findAll(){
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(x-> new CategoryDto(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> objOptional = categoryRepository.findById(id);
		Category category = objOptional.orElseThrow(() -> new ControllerNotFoundException(" Entity not found"));
		return new CategoryDto(category);
	}
	@Transactional(readOnly = true)
	public CategoryDto insert(CategoryDto dto) {
		Category category = new Category();
		category.setName(dto.getName());
		categoryRepository.save(category);
		return new CategoryDto(category);
	}
	@Transactional
	public  CategoryDto update(Long id, CategoryDto dto) {
		try {
			Category category = categoryRepository.getOne(id);
			category.setName(dto.getName());
			category = categoryRepository.save(category);
			return new CategoryDto(category);
		} 
		catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
	}
}

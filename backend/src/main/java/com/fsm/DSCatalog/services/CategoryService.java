package com.fsm.DSCatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.DSCatalog.Dto.CategoryDto;
import com.fsm.DSCatalog.entities.Category;
import com.fsm.DSCatalog.repositories.CategoryRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Transactional(readOnly = true)
	public Page<CategoryDto> findAll(PageRequest pageRequest){
		Page<Category> list = categoryRepository.findAll(pageRequest);
		return list.map(x-> new CategoryDto(x));
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
	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violetion");
		}
		
	}
}

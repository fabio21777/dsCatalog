package com.fsm.DSCatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.DSCatalog.Dto.ProductDto;
import com.fsm.DSCatalog.entities.Product;
import com.fsm.DSCatalog.repositories.ProductRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Transactional(readOnly = true)
	public Page<ProductDto> findAll(PageRequest pageRequest){
		Page<Product> list = productRepository.findAll(pageRequest);
		return list.map(x-> new ProductDto(x));
	}
	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		Optional<Product> objOptional = productRepository.findById(id);
		Product product = objOptional.orElseThrow(() -> new ControllerNotFoundException(" Entity not found"));
		return new ProductDto(product,product.getCategories());
	}
	
	
	/*@Transactional(readOnly = true)
	public ProductDto insert(ProductDto dto) {
		Product product = new Product();
		product.setName(dto.getName());
		productRepository.save(product);
		return new ProductDto(product);
	}
	@Transactional
	public  ProductDto update(Long id, ProductDto dto) {
		try {
			Product product = productRepository.getOne(id);
			product.setName(dto.getName());
			product = productRepository.save(product);
			return new ProductDto(product);
		} 
		catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
	}*/
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violetion");
		}
		
	}
}

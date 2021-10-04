package com.fsm.DSCatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.DSCatalog.Dto.RoleDto;
import com.fsm.DSCatalog.entities.Role;
import com.fsm.DSCatalog.repositories.RoleRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Transactional(readOnly = true)
	public Page<RoleDto> findAll(Pageable pageable){
		Page<Role> list = roleRepository.findAll(pageable);
		return list.map(x-> new RoleDto(x));
	}
	
	
	@Transactional(readOnly = true)
	public RoleDto findById(Long id) {
		Optional<Role> objOptional = roleRepository.findById(id);
		Role role = objOptional.orElseThrow(() -> new ControllerNotFoundException(" Entity not found"));
		return new RoleDto(role);
	}
	
	
	@Transactional
	public RoleDto insert(RoleDto dto) {
		Role role = new Role();
		copyDtoToEntity(dto,role);
		roleRepository.save(role);
		return new RoleDto(role);
	}
	@Transactional
	public  RoleDto update(Long id, RoleDto dto) {
		try {
			Role role = roleRepository.getOne(id);
			copyDtoToEntity(dto,role);
			role = roleRepository.save(role);
			return new RoleDto(role);
		} 
		catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			roleRepository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violetion");
		}
		
	}
	
	private void copyDtoToEntity(RoleDto dto, Role entity) {
	}	
	
	

}

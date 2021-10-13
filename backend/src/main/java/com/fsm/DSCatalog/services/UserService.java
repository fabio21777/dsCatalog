package com.fsm.DSCatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.DSCatalog.Dto.UserDto;
import com.fsm.DSCatalog.Dto.UserInsertDto;
import com.fsm.DSCatalog.Dto.UserUpdateDto;
import com.fsm.DSCatalog.entities.User;
import com.fsm.DSCatalog.repositories.RoleRepository;
import com.fsm.DSCatalog.repositories.UserRepository;
import com.fsm.DSCatalog.services.exception.ControllerNotFoundException;
import com.fsm.DSCatalog.services.exception.DataBaseException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public Page<UserDto> findAll(Pageable pageable){
		Page<User> list = userRepository.findAll(pageable);
		return list.map(x-> new UserDto(x));
	}
	
	
	@Transactional(readOnly = true)
	public UserDto findById(Long id) {
		Optional<User> objOptional = userRepository.findById(id);
		User user = objOptional.orElseThrow(() -> new ControllerNotFoundException(" Entity not found"));
		return new UserDto(user);
	}
	
	
	@Transactional
	public UserDto insert(UserInsertDto dto) {
		User user = new User();
		copyDtoToEntity(dto,user);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		userRepository.save(user);
		return new UserDto(user);
	}
	@Transactional
	public  UserDto update(Long id, UserUpdateDto dto) {
		try {
			User user = userRepository.getOne(id);
			copyDtoToEntity(dto,user);
			user = userRepository.save(user);
			return new UserDto(user);
		} 
		catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("ID NOT FOUND" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violetion");
		}
		
	}
	
	private void copyDtoToEntity(UserDto dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		dto.getRoles().forEach(role -> entity.getRoles().add(roleRepository.getOne(role.getId())));
	}

	
	

}

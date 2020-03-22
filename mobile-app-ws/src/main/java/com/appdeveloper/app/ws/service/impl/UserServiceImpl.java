package com.appdeveloper.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appdeveloper.app.ws.exceptions.UserServiceException;
import com.appdeveloper.app.ws.io.entity.UserEntity;
import com.appdeveloper.app.ws.io.repositories.UserRepository;
import com.appdeveloper.app.ws.service.UserService;
import com.appdeveloper.app.ws.shared.Utils;
import com.appdeveloper.app.ws.shared.dto.AddressDto;
import com.appdeveloper.app.ws.shared.dto.UserDto;
import com.appdeveloper.app.ws.ui.model.response.ErrorMessages;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCrptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		// TODO Auto-generated method stub
		
		if (userRepository.findByEmail(user.getEmail()) != null ) throw new RuntimeException("Record already exist");
		
		
		int len = user.getAddresses().size();
		
		for(int i=0; i<len; i++) {
			AddressDto address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddressId(30));
			user.getAddresses().set(i, address);
		}
		
		//UserEntity userEntity = new UserEntity();
		//BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper  = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		
		userEntity.setEncryptedPassword(bCrptPasswordEncoder.encode(user.getPassword()));
		
		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId(publicUserId);
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		//UserDto returnValue = new UserDto();
		//BeanUtils.copyProperties(storedUserDetails, returnValue);
		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
		return returnValue;
	}
	
	@Override 
	public UserDto getUser(String email) throws UsernameNotFoundException{
		UserEntity entity = userRepository.findByEmail(email);
		
		if (entity == null) throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(entity, returnValue);
		return returnValue;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity = userRepository.findByUserId(userId);
		
		if(entity == null) throw new UsernameNotFoundException(userId); // Similar to updateUser method , we can use UserServiceException
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(entity, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		
		UserEntity entity = userRepository.findByUserId(userId);
		
		if (entity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		entity.setFirstName(user.getFirstName());
		entity.setLastName(user.getLastName());
		UserEntity updatedEntity = userRepository.save(entity);
		
		UserDto returnValue = new UserDto();
		
		BeanUtils.copyProperties(updatedEntity, returnValue);
		
		
		return returnValue;
	}

	@Override
	public Boolean deleteUserByUserId(String userId) {
		
		UserEntity entity = userRepository.findByUserId(userId);
		if (entity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(entity);
		
		return true;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		// TODO Auto-generated method stub
		//Iterable<UserEntity> entityUsers = userRepository.findAll();
		if (page > 0) page--;
		
		List<UserDto> returnvalue = new ArrayList<UserDto>();
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> pagedUsers = userRepository.findAll(pageableRequest);
		
		List<UserEntity> users = pagedUsers.getContent();
		
		for(UserEntity userEntity : users) {
			UserDto oneUser = new UserDto();
			BeanUtils.copyProperties(userEntity, oneUser);
			returnvalue.add(oneUser);
		}
		
		
		return returnvalue;
	}

}

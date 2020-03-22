package com.appdeveloper.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appdeveloper.app.ws.io.entity.AddressEntity;
import com.appdeveloper.app.ws.io.entity.UserEntity;
import com.appdeveloper.app.ws.io.repositories.AddressRepository;
import com.appdeveloper.app.ws.io.repositories.UserRepository;
import com.appdeveloper.app.ws.service.AddressService;
import com.appdeveloper.app.ws.shared.dto.AddressDto;
import com.appdeveloper.app.ws.shared.dto.UserDto;


@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDto> getAddressesByUserId(String userId) {
		// TODO Auto-generated method stub
		List<AddressDto> returnValue = new ArrayList<>();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) return returnValue; // Similar to updateUser method , we can use UserServiceException
		
		ModelMapper modelMapper = new ModelMapper();
		
		Iterable<AddressEntity> addresses = addressRepository.getAllByUserDetails(userEntity);
		
		for(AddressEntity adress : addresses) {
			returnValue.add(modelMapper.map(adress, AddressDto.class));
		}
		
		
		return returnValue;
	}

	@Override
	public AddressDto getAddressesByAddressId(String addressId) {
		
		AddressDto returnValue = new AddressDto();
		AddressEntity addressEntity = addressRepository.findByAddressId( addressId);
		
		returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
		
		return returnValue;
	}

}

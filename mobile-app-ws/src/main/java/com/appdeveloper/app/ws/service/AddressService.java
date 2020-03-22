package com.appdeveloper.app.ws.service;

import java.util.List;

import com.appdeveloper.app.ws.shared.dto.AddressDto;



public interface AddressService {

	List<AddressDto> getAddressesByUserId(String userId);
	AddressDto getAddressesByAddressId(String addressId);
}

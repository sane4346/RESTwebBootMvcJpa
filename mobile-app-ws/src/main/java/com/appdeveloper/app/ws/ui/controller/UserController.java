package com.appdeveloper.app.ws.ui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdeveloper.app.ws.ui.model.request.*;
import com.appdeveloper.app.ws.ui.model.response.ErrorMessages;
import com.appdeveloper.app.ws.ui.model.response.OperationStatusModel;
import com.appdeveloper.app.ws.ui.model.response.RequestOperationName;
import com.appdeveloper.app.ws.ui.model.response.RequestOperationStatus;
import com.appdeveloper.app.ws.ui.model.response.UserRest;
import com.appdeveloper.app.ws.exceptions.UserServiceException;
import com.appdeveloper.app.ws.service.UserService;
import com.appdeveloper.app.ws.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("users")  //http://localhost:8080/users

public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path = "/{id}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String id)
	{
		UserRest returnValue = new UserRest();
		
		UserDto dto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(dto, returnValue);
		
		return returnValue;
	}
	
	@PostMapping( consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception
	{
		UserRest returnValue = new UserRest();
		
		if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;
	}
	
	@PutMapping( path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails)
	{
		UserRest returnValue = new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUsr = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updatedUsr, returnValue);
		
		
		return returnValue;
		
	}
	
	@DeleteMapping(path = "/{id}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public OperationStatusModel deleteUser(@PathVariable String id)
	{
		OperationStatusModel operationStatus = new OperationStatusModel();
		operationStatus.setOperationName(RequestOperationName.DELETE.name());
		if (userService.deleteUserByUserId(id))
			operationStatus.setOperationResult(RequestOperationStatus.SUCESS.name());
		else 
			operationStatus.setOperationResult(RequestOperationStatus.ERROR.name());
	    return operationStatus;
	}
	
	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="limit",defaultValue="1")int limit){
		List<UserRest> usersValue = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(page,limit);
		
		for(UserDto user : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(user, userModel);
			usersValue.add(userModel);
			}
		
		return usersValue;
	}

}

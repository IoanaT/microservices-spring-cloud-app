package com.microservices.spring.cloud.users.ws.userservice.impl;

import java.util.HashMap;
import java.util.Map;

import com.microservices.spring.cloud.users.ws.shared.UserDTO;
import com.microservices.spring.cloud.users.ws.shared.Utils;
import com.microservices.spring.cloud.users.ws.ui.model.request.UserDetailsRequestModel;
import com.microservices.spring.cloud.users.ws.ui.model.response.UserRest;
import com.microservices.spring.cloud.users.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	Map<String, UserRest> users;
	Utils utils;
	
	public UserServiceImpl() {}
	
	@Autowired
	public UserServiceImpl(Utils utils)
	{
		this.utils =utils;
	}
	
	@Override
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		
		UserRest returnValue = new UserRest();
		returnValue.setEmail(userDetails.getEmail());
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		
		String userId = utils.generateUserId();
		returnValue.setUserId(userId);
		
		if(users == null) users = new HashMap<>();
		users.put(userId, returnValue);
		
		return returnValue;
		
	}

	@Override
	public UserRest createUser(UserDTO userDTO) {
		UserRest returnValue = new UserRest();
		returnValue.setEmail(userDTO.getEmail());
		returnValue.setFirstName(userDTO.getFirstName());
		returnValue.setLastName(userDTO.getLastName());

		String userId = utils.generateUserId();
		returnValue.setUserId(userId);

		if(users == null) users = new HashMap<>();
		users.put(userId, returnValue);

		return returnValue;
	}

}

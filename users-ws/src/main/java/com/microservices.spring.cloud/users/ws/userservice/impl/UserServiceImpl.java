package com.microservices.spring.cloud.users.ws.userservice.impl;

import java.util.HashMap;
import java.util.Map;

import com.microservices.spring.cloud.users.ws.data.UserEntity;
import com.microservices.spring.cloud.users.ws.data.UserRepository;
import com.microservices.spring.cloud.users.ws.shared.UserDTO;
import com.microservices.spring.cloud.users.ws.shared.Utils;
import com.microservices.spring.cloud.users.ws.ui.model.request.UserDetailsRequestModel;
import com.microservices.spring.cloud.users.ws.ui.model.response.UserRest;
import com.microservices.spring.cloud.users.ws.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	Map<String, UserRest> users;
	Utils utils;
	UserRepository userRepository;
	
	public UserServiceImpl() {}
	
	@Autowired
	public UserServiceImpl(Utils utils, UserRepository userRepository)
	{
		this.utils =utils;
		this.userRepository = userRepository;
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
	public UserDTO createUser(UserDTO userDTO) {

		userDTO.setUserId(utils.generateUserId());
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
		userEntity.setEncryptedPassword("Test");
		userRepository.save(userEntity);

		UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);

		return returnValue;
	}

}

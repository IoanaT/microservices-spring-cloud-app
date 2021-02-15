package com.microservices.spring.cloud.users.ws.userservice;

import com.microservices.spring.cloud.users.ws.shared.UserDTO;
import com.microservices.spring.cloud.users.ws.ui.model.request.UserDetailsRequestModel;
import com.microservices.spring.cloud.users.ws.ui.model.response.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	UserRest createUser(UserDetailsRequestModel userDetails);
	UserDTO createUser(UserDTO userDTO);
	UserDTO getUserDetailsByEmail(String email);
}

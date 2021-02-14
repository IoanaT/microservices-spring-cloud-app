package com.microservices.spring.cloud.users.ws.userservice;

import com.microservices.spring.cloud.users.ws.shared.UserDTO;
import com.microservices.spring.cloud.users.ws.ui.model.request.UserDetailsRequestModel;
import com.microservices.spring.cloud.users.ws.ui.model.response.UserRest;

public interface UserService {
	UserRest createUser(UserDetailsRequestModel userDetails);
	UserRest createUser(UserDTO userDTO);
}

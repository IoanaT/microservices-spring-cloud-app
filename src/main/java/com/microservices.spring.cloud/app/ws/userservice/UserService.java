package com.microservices.spring.cloud.app.ws.userservice;

import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.microservices.spring.cloud.app.ws.ui.model.request.UserDetailsRequestModel;

public interface UserService {
	UserRest createUser(UserDetailsRequestModel userDetails);
}

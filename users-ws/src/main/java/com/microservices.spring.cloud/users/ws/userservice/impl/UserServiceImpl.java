package com.microservices.spring.cloud.users.ws.userservice.impl;

import java.util.*;

import com.microservices.spring.cloud.users.ws.data.UserEntity;
import com.microservices.spring.cloud.users.ws.data.UserRepository;
import com.microservices.spring.cloud.users.ws.exceptions.UserServiceException;
import com.microservices.spring.cloud.users.ws.shared.UserDTO;
import com.microservices.spring.cloud.users.ws.shared.Utils;
import com.microservices.spring.cloud.users.ws.ui.model.request.UserDetailsRequestModel;
import com.microservices.spring.cloud.users.ws.ui.model.response.AlbumResponseModel;
import com.microservices.spring.cloud.users.ws.ui.model.response.UserRest;
import com.microservices.spring.cloud.users.ws.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class UserServiceImpl implements UserService {

    Map<String, UserRest> users;
    Utils utils;
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    RestTemplate restTemplate;
    Environment environment;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(Utils utils, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           RestTemplate restTemplate,
                           Environment environment) {
        this.utils = utils;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {

        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());

        String userId = utils.generateUserId();
        returnValue.setUserId(userId);

        if (users == null) users = new HashMap<>();
        users.put(userId, returnValue);

        return returnValue;

    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        userDTO.setUserId(utils.generateUserId());
        userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userRepository.save(userEntity);

        UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (Objects.isNull(userEntity))
            throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (Objects.isNull(userEntity))
            throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            throw new UserServiceException("User not found!");
        UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);
        //use albums microservice name under which it's registered with discovery service Eureka
        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
        ResponseEntity<List<AlbumResponseModel>> response = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        });
        List<AlbumResponseModel> albumsList = response.getBody();
        userDTO.setAlbums(albumsList);
        return userDTO;
    }
}

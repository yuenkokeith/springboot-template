package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.UserDto;
import com.microservices.accountservice.response.UserResponse;
import com.microservices.accountservice.repository.UserRepository;

public interface UserService {

    UserDto saveUser(UserDto userDto);
    UserRepository getUserRepository();

    UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);

    UserDto updateUser(UserDto userDto, long id);
}

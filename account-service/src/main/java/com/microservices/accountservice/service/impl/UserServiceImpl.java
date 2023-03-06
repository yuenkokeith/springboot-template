package com.microservices.accountservice.service.impl;

import com.microservices.accountservice.dto.UserDto;
import com.microservices.accountservice.response.UserResponse;
import com.microservices.accountservice.entity.User;
import com.microservices.accountservice.repository.UserRepository;
import com.microservices.accountservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private ModelMapper mapper;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        UserDto newUserDto = new UserDto();
        return newUserDto;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);

        LOGGER.info("User List: ", users);

        // get content for page object
        List<User> listOfUsers = users.getContent();

        List<UserDto> content = listOfUsers.stream().map(user -> mapToDto(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    @Override
    public UserDto updateUser(UserDto userDto, long id) {

        User user = userRepository.findById(id).get();
        user.setName(userDto.getName());
        User updatedUser = userRepository.save(user);
        UserDto updatedUserDto = mapper.map(updatedUser, UserDto.class);

        return updatedUserDto;
    }

    // Convert Entity to Dto
    private UserDto mapToDto(User user) {

        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    private User mapToEntity(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return user;
    }

}

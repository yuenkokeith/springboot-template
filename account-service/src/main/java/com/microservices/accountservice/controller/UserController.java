package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.RegisterDto;
import com.microservices.accountservice.dto.RoleDto;
import com.microservices.accountservice.service.AuthService;
import com.microservices.accountservice.service.RoleService;
import com.microservices.accountservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/user")
public class UserController {

    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private UserService userService;
    private RoleService roleService;
    private AuthService authService;

    public UserController(UserService userService, RoleService roleService, AuthService authService) {
        this.userService = userService;
        this.roleService = roleService;
        this.authService = authService;
    }

    // Build Paging Records
    /*
    @GetMapping
    public UserResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return userService.getAllUsers(pageNo, pageSize, sortBy, sortDir);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto ,
                                              @PathVariable(name = "id") long id) {

        UserDto updateUserDto = userService.updateUser(userDto, id);

        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);

    }
    */

    @GetMapping("initial")
    public String initalSystem() {
        LOGGER.info("[initalSystem]");

        // add role record
        RoleDto userRoleDto = new RoleDto();
        RoleDto adminRoleDto = new RoleDto();
        userRoleDto.setName("ROLE_USER");
        userRoleDto.setCreatedAt(LocalDateTime.now());
        userRoleDto.setUpdatedAt(LocalDateTime.now());

        adminRoleDto.setName("ROLE_ADMIN");
        adminRoleDto.setCreatedAt(LocalDateTime.now());
        adminRoleDto.setUpdatedAt(LocalDateTime.now());

        roleService.saveRole(userRoleDto);
        roleService.saveRole(adminRoleDto);

        // create default admin system account
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345678"));

        RegisterDto registerDto =  new RegisterDto();
        registerDto.setName("admin");
        registerDto.setUsername("admin");
        registerDto.setPassword("");
        registerDto.setEmail("admin@admin.com");
        authService.register(registerDto, "ROLE_ADMIN");

        return "System Initialed";

    }

}


//comment
package com.microservices.accountservice.service.impl;

import com.microservices.accountservice.dto.LoginDto;
import com.microservices.accountservice.dto.RegisterDto;
import com.microservices.accountservice.entity.Role;
import com.microservices.accountservice.entity.User;
import com.microservices.accountservice.exception.AccountAPIException;
import com.microservices.accountservice.repository.RoleRepository;
import com.microservices.accountservice.repository.UserRepository;
import com.microservices.accountservice.security.JwtTokenProvider;
import com.microservices.accountservice.service.AuthService;
import com.microservices.accountservice.service.RoleService;
import com.microservices.accountservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AuthenticationManager authenticationManager;

    private UserService userService;
    private RoleRepository roleRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // After login and generate token
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto, String role) {

        UserRepository userRepository = userService.getUserRepository();
        RoleRepository roleRepository = roleService.getRoleRepository();

        // add check for username exist in database
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        LOGGER.info("[register] registerDTO: email:{}, username:{}, password:{}, role:{}", registerDto.getEmail(), registerDto.getUsername(), registerDto.getPassword(), role);

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword( passwordEncoder.encode(registerDto.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());

        Set<Role> roles = new HashSet<>();

        Role userRole;

        if(role!=null) {
            if(role.equals("admin")) {
                LOGGER.info("create Admin account!");
                userRole = roleRepository.findByName("ROLE_ADMIN").get();
            } else {
                LOGGER.info("create User account!");
                userRole = roleRepository.findByName("ROLE_USER").get();
            }
        } else {
            LOGGER.info("create User account!");
            userRole = roleRepository.findByName("ROLE_USER").get();
        }

        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }
}

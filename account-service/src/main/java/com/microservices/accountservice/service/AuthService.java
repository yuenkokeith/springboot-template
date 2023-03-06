package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.LoginDto;
import com.microservices.accountservice.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto, String role);
}

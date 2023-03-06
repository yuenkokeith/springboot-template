package com.microservices.accountservice.controller;

import com.microservices.accountservice.response.JWTAuthResponse;
import com.microservices.accountservice.dto.LoginDto;
import com.microservices.accountservice.dto.RegisterDto;
import com.microservices.accountservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register/{role}", "/register"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto,
                                           @PathVariable(required = false, name ="role") String role) {
        String response = authService.register(registerDto, role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

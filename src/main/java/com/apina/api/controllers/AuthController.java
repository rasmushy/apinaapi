package com.apina.api.controllers;

import com.apina.api.dtos.LoginUserDTO;
import com.apina.api.dtos.UserDTO;
import com.apina.api.models.LoginResponse;
import com.apina.api.models.UserEntity;
import com.apina.api.services.AuthenticationService;
import com.apina.api.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//AuthController.java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginResponse> register(@RequestBody UserDTO userDto) {
        if (authenticationService.exists(userDto.getUsername())) {
            LOGGER.info("User already exists: {}", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String jwtToken = jwtService.generateToken(authenticationService.register(userDto).toUserEntity());
        return ResponseEntity.ok(new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()));
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto).toUserEntity();
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()));
    }

}

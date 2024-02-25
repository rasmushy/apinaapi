package com.apina.api.services;

import com.apina.api.dtos.LoginUserDTO;
import com.apina.api.dtos.UserDTO;
import com.apina.api.models.UserEntity;
import com.apina.api.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginUserDTO register(UserDTO input) {
        if(input.getRole() == null) {
            input = input.setRole("USER");
        }
        UserEntity user = input.toUserEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        user = userRepository.save(user);
        return new LoginUserDTO(user);
    }

    @Override
    public LoginUserDTO authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        UserEntity user = userRepository.findByUsername(input.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        return new LoginUserDTO(user);
    }

    @Override
    public boolean exists(String name) {
        return userRepository.findByUsername(name).isPresent();
    }
}

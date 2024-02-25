package com.apina.api.services;

import com.apina.api.dtos.LoginUserDTO;
import com.apina.api.dtos.UserDTO;
public interface AuthenticationService {
    LoginUserDTO register(UserDTO input);
    LoginUserDTO authenticate(LoginUserDTO input);
    boolean exists(String name);
}

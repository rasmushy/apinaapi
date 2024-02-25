package com.apina.api.dtos;

import com.apina.api.models.UserEntity;
public record LoginUserDTO(String username, String password) {

    public LoginUserDTO(UserEntity userEntity) {
        this(userEntity.getUsername(), userEntity.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDTO setName(String name) {
        return new LoginUserDTO(name, this.password);
    }

    public LoginUserDTO setPassword(String password) {
        return new LoginUserDTO(this.username, password);
    }

    public String toString() {
        return "LoginUserDTO(name=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }

    public UserEntity toUserEntity() {
        return new UserEntity(this.getUsername(), this.getPassword());
    }

}

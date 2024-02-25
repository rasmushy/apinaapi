package com.apina.api.services;

import com.apina.api.dtos.UserDTO;

import java.util.List;
import java.util.Optional;
public interface UserService {
    Optional<UserDTO> findByUsername(String name);

    List<UserDTO> findAll();

    UserDTO findOne(String id);
    long deleteAll();
    UserDTO update(UserDTO userEntity);
    long delete(String id);
    long delete(List<String> ids);
    long count();
    List<UserDTO> saveAll(List<UserDTO> userEntities);

    List<UserDTO> findAll(List<String> ids);

}

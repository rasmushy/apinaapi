package com.apina.api.services;

import com.apina.api.dtos.UserDTO;
import com.apina.api.models.UserEntity;
import com.apina.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// UserServiceImpl.java
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> findByUsername(String name) {
        return userRepository.findByUsername(name).map(UserDTO::new);
    }

    @Override
    public long deleteAll() {
        return userRepository.deleteAll();
    }

    @Override
    public UserDTO update(UserDTO userEntity) {
        UserEntity user = userEntity.toUserEntity();
        user.setUpdatedAt(new Date());
        return new UserDTO(userRepository.update(user));
    }
    @Override
    public long delete(String id) {
        return userRepository.delete(id);
    }
    @Override
    public long delete(List<String> ids) {
        return userRepository.delete(ids);
    }
    @Override
    public long count() {
        return userRepository.count();
    }
    @Override
    public List<UserDTO> saveAll(List<UserDTO> userEntities) {
        return userEntities.stream()
                .map(UserDTO::toUserEntity)
                .peek(userRepository::save)
                .map(UserDTO::new)
                .toList();

    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    @Override
    public List<UserDTO> findAll(List<String> ids) {
        return userRepository.findAll(ids).stream().map(UserDTO::new).toList();
    }

    @Override
    public UserDTO findOne(String id) {
        UserEntity userEntity = userRepository.findOne(id);
        return new UserDTO(userEntity);
    }

}

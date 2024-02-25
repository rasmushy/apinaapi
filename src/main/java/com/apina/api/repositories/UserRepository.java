package com.apina.api.repositories;

import com.apina.api.models.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository {
    UserEntity save(UserEntity userEntity);

    List<UserEntity> saveAll(List<UserEntity> userEntities);
    Optional<UserEntity> findByUsername(String name);

    List<UserEntity> findAll();

    UserEntity findOne(String id);

    List<UserEntity> findAll(List<String> ids);
    long count();
    long delete(String id);

    long delete(List<String> ids);
    long deleteAll();
    UserEntity update(UserEntity userEntity);
}

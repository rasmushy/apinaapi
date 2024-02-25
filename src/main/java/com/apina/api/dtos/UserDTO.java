package com.apina.api.dtos;

import com.apina.api.models.UserEntity;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
public record UserDTO(
        String id,
        String username,
        String role,
        String password,
        Date createdAt,
        Date updatedAt
) {
    public UserDTO(UserEntity userEntity) {
        this(userEntity.getId().toString(), userEntity.getUsername(), userEntity.getRole().get(0), userEntity.getPassword(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public UserDTO setRole(String role) {
        return new UserDTO(this.id, this.username, role, this.password, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
               "id='" + id + '\'' +
               ", username='" + username + '\'' +
               ", role='" + role + '\'' +
               ", password='" + password + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }

    public UserEntity toUserEntity() {
        ObjectId _id = id == null ? null : new ObjectId(id);
        return new UserEntity(_id, username, List.of(role), password, createdAt, updatedAt);
    }
}

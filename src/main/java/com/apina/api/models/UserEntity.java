package com.apina.api.models;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
public class UserEntity implements UserDetails {

    private ObjectId id;
    private String username;

    private List<String> role;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    public UserEntity() {
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(ObjectId id, String username, List<String> role, String password, Date createdAt, Date updatedAt) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getId() {
        return id;
    }

    public UserEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public List<String> getRole() {
        return role;
    }

    public UserEntity setRole(List<String> role) {
        this.role = role;
        return this;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // MongoDB cant read authorities object from the database, so we just return empty list
        return List.of();
    }

    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public UserEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public UserEntity setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", name='" + username + '\'' + ", role=" + role + ", password='" + password + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserEntity that))
            return false;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(role, that.role) && Objects.equals(password, that.password) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role, password, createdAt, updatedAt);
    }
}

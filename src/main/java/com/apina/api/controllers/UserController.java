package com.apina.api.controllers;

import com.apina.api.dtos.UserDTO;
import com.apina.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.apina.api.util.ControllerUtils.isAdmin;
import static com.apina.api.util.ControllerUtils.isCurrentUser;
//UserController.java
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("info")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("getUserInfo: {}", authentication);
        if (authentication.getPrincipal() == "anonymousUser") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDTO currentUser = userService.findByUsername(authentication.getName()).orElse(null);
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("getUser: {}", authentication);
        if (isAdmin(authentication)) {
            UserDTO user = userService.findByUsername(username).orElse(null);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("getAllUsers: {}", authentication);
        List<UserDTO> users = Collections.emptyList();
        if (isAdmin(authentication)) {
            users = userService.findAll();
            return users;

        }
        return users;
    }

    @DeleteMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("deleteUser: {}", authentication);
        if (isAdmin(authentication)) {
            userService.delete(username);
            return ResponseEntity.ok("Deleted: " + username);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("deleteAllUsers: {}", authentication);
        if (isAdmin(authentication)) {
            long deletedAmount = userService.deleteAll();
            return ResponseEntity.ok("Deleted: " + deletedAmount + " users");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateUser(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("updateUser: {}", authentication);
        if (isAdmin(authentication) || isCurrentUser(authentication, username)) {
            UserDTO user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(userService.update(user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}


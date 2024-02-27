package com.apina.api.util;

import org.springframework.security.core.Authentication;
public class ControllerUtils {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static boolean isAdmin(Authentication authentication) {
        return authentication.isAuthenticated() && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN));
    }

    public static boolean isCurrentUser(Authentication authentication, String username) {
        return authentication.isAuthenticated() && authentication.getName().equals(username);
    }

    public static boolean isUser(Authentication authentication) {
        return authentication.isAuthenticated() && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }

}


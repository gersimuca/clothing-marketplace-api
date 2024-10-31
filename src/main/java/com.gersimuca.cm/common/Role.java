package com.gersimuca.cm.common;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    CLIENT("client");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); // Add the current role as an authority
        return authorities;
    }

    public static List<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return role.getAuthorities();
    }
}

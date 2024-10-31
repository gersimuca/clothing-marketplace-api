package com.gersimuca.cm.common;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
  CLIENT("CLIENT"),
  ADMIN("ADMIN");

  private final String roleName;

  Role(String roleName) {
    this.roleName = roleName;
  }

  public List<SimpleGrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(
        new SimpleGrantedAuthority("ROLE_" + this.name())); // Add the current role as an authority
    return authorities;
  }

  public static List<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
    return role.getAuthorities();
  }
}

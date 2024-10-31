package com.gersimuca.cm.feature.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
  @NotEmpty(message = "Username cannot be empty or null")
  private String username;

  @NotEmpty(message = "Password cannot be empty or null")
  private String password;

  @NotEmpty(message = "Full Name cannot be empty or null")
  private String fullname;
}

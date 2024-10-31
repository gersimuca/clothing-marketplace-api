package com.gersimuca.cm.feature.user;

import static org.springframework.http.ResponseEntity.ok;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/create")
  public ResponseEntity<UserDto> createUser(
      @Valid @RequestBody UserCreateRequest userCreateRequest) {
    return ok(userService.createUser(userCreateRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<UserDto> getUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
    return ok(userService.login(userLoginRequest));
  }
}

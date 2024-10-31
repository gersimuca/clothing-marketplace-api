package com.gersimuca.cm.feature.user;

import static org.springframework.http.ResponseEntity.ok;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/create")
  public ResponseEntity<UserDto> createUser(@RequestBody UserCreateRequest userCreateRequest) {
    return ok(userService.createUser(userCreateRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<UserDto> getUser(@RequestBody UserLoginRequest userLoginRequest) {
    return ok(userService.getUser(userLoginRequest));
  }
}

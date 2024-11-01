package com.gersimuca.cm.feature.user;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserControllerTest {
  @Mock private UserService service;
  @InjectMocks private UserController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new UserController(service);
    var dto = UserTestData.JOHN_SMITH_DTO;
    when(service.createUser(any(UserCreateRequest.class))).thenReturn(dto);
  }

  @Test
  void createUser() {
    controller.createUser(UserTestData.USER_CREATE_REQUEST);
    verify(service).createUser(any(UserCreateRequest.class));
  }

  @Test
  void loginUser() {
    controller.getUser(UserTestData.USER_LOGIN_REQUEST);
    verify(service).login(any(UserLoginRequest.class));
  }
}

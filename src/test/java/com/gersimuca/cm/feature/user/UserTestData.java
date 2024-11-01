package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.Role;
import com.gersimuca.cm.feature.token.TokenDto;
import com.gersimuca.cm.feature.token.TokenEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestData {
  public static final UserCreateRequest USER_CREATE_REQUEST =
      new UserCreateRequest("johnsmith", "{noop}password123", "John Smith", "800 E Dimond Blvd");

  public static final UserLoginRequest USER_LOGIN_REQUEST =
      new UserLoginRequest("johnsmith", "{noop}password123");

  public static final UserEntity JOHN_SMITH_ENTITY =
      UserEntity.builder()
          .id(1L)
          .username("johnsmith")
          .password("{noop}password123")
          .fullName("John Smith")
          .address("456 Elm St")
          .role(Role.CLIENT)
          .build();

  public static final UserEntity MARIA_SANTOS_ENTITY =
      UserEntity.builder()
          .id(2L)
          .username("mariasantos")
          .password("{noop}securepass")
          .fullName("Maria Santos")
          .address("789 Pine Ave")
          .role(Role.CLIENT)
          .build();

  public static final TokenDto TOKEN_DTO = TokenDto.builder().token("sample-jwt-token-123").build();

  public static final UserDto JOHN_SMITH_DTO =
      UserDto.builder()
          .id(1L)
          .username("johnsmith")
          .fullname("John Smith")
          .address("456 Elm St")
          .token(TOKEN_DTO)
          .build();

  public static final UserDto MARIA_SANTOS_DTO =
      UserDto.builder()
          .id(2L)
          .username("mariasantos")
          .fullname("Maria Santos")
          .address("789 Pine Ave")
          .token(null)
          .build();

  public static final TokenEntity ACTIVE_TOKEN =
      TokenEntity.builder()
          .id(1)
          .token("activeSampleToken123")
          .tokenType("BEARER")
          .expired(false)
          .revoked(false)
          .user(UserTestData.JOHN_SMITH_ENTITY)
          .build();

  public static final TokenEntity EXPIRED_TOKEN =
      TokenEntity.builder()
          .id(2)
          .token("expiredSampleToken456")
          .tokenType("BEARER")
          .expired(true)
          .revoked(false)
          .user(UserTestData.JOHN_SMITH_ENTITY)
          .build();

  public static final TokenEntity REVOKED_TOKEN =
      TokenEntity.builder()
          .id(3)
          .token("revokedSampleToken789")
          .tokenType("BEARER")
          .expired(false)
          .revoked(true)
          .user(UserTestData.JOHN_SMITH_ENTITY) // Another user for diversity
          .build();
}

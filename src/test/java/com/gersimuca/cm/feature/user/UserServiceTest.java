package com.gersimuca.cm.feature.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.gersimuca.cm.common.exception.EntityNotFoundException;
import com.gersimuca.cm.configuration.JwtService;
import com.gersimuca.cm.feature.token.TokenDto;
import com.gersimuca.cm.feature.token.TokenEntity;
import com.gersimuca.cm.feature.token.TokenMapper;
import com.gersimuca.cm.feature.token.TokenRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
  @Mock private UserMapper mapper;
  @Mock private UserRepository userRepository;
  @Mock private JwtService jwtService;
  @Mock private TokenRepository tokenRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private TokenMapper tokenMapper;

  @InjectMocks private UserService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service =
        new UserService(
            mapper,
            userRepository,
            jwtService,
            tokenRepository,
            passwordEncoder,
            authenticationManager,
            tokenMapper);

    when(userRepository.findAll()).thenReturn(List.of(UserTestData.JOHN_SMITH_ENTITY));
    when(userRepository.findById(UserTestData.JOHN_SMITH_ENTITY.getId()))
        .thenReturn(Optional.of(UserTestData.JOHN_SMITH_ENTITY));
    when(userRepository.findById(
            argThat(userId -> !userId.equals(UserTestData.JOHN_SMITH_ENTITY.getId()))))
        .thenReturn(Optional.empty());
  }

  @Test
  void usernameExisting() {
    final String existingUsername = UserTestData.JOHN_SMITH_ENTITY.getUsername();
    when(userRepository.existsByUsername(existingUsername)).thenReturn(true);
    service.existsByUsername(existingUsername);
    verify(userRepository).existsByUsername(existingUsername);
  }

  @Test
  void usernameNotExisting() {
    final String existingUsername = "INVALID";
    when(userRepository.existsByUsername(existingUsername)).thenReturn(false);
    service.existsByUsername(existingUsername);
    verify(userRepository).existsByUsername(existingUsername);
  }

  @Test
  void createUser() {

    UserCreateRequest userCreateRequest = UserTestData.USER_CREATE_REQUEST;

    UserEntity userEntity = UserTestData.JOHN_SMITH_ENTITY;
    UserDto expectedUserDto = UserTestData.JOHN_SMITH_DTO;
    TokenEntity savedToken =
        TokenEntity.builder()
            .id(1)
            .user(userEntity)
            .token("generated-jwt-token")
            .revoked(false)
            .expired(false)
            .build();

    when(userRepository.existsByUsername(userCreateRequest.getUsername())).thenReturn(false);
    when(passwordEncoder.encode(userCreateRequest.getPassword())).thenReturn("encodedPassword");
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
    when(jwtService.generateToken(userEntity)).thenReturn("generated-jwt-token");
    when(tokenRepository.save(any(TokenEntity.class))).thenReturn(savedToken);
    when(mapper.mapToDto(userEntity)).thenReturn(expectedUserDto);
    when(tokenMapper.mapToDto(savedToken)).thenReturn(UserTestData.JOHN_SMITH_DTO.getToken());

    UserDto actualUserDto = service.createUser(userCreateRequest);

    assertNotNull(actualUserDto);
    assertEquals(expectedUserDto, actualUserDto);
    verify(userRepository).save(any(UserEntity.class));
    verify(jwtService).generateToken(userEntity);
    verify(tokenRepository).save(any(TokenEntity.class));
    verify(mapper).mapToDto(userEntity);
    verify(tokenMapper).mapToDto(savedToken);
  }

  @Test
  void login() {
    UserLoginRequest userLoginRequest = new UserLoginRequest("testuser", "password");
    UserEntity userEntity = UserTestData.JOHN_SMITH_ENTITY;
    TokenEntity tokenEntity = UserTestData.ACTIVE_TOKEN;
    TokenDto tokenDto = UserTestData.TOKEN_DTO;
    UserDto expectedUserDto = UserTestData.JOHN_SMITH_DTO;

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(null);

    when(userRepository.findByUsername(userLoginRequest.username()))
        .thenReturn(Optional.of(userEntity));
    when(tokenRepository.findAllValidTokenByUser(userEntity.getId()))
        .thenReturn(List.of(tokenEntity));

    when(jwtService.generateToken(userEntity)).thenReturn("sample-jwt-token-123");
    when(tokenMapper.mapToDto(any(TokenEntity.class))).thenReturn(tokenDto);
    when(mapper.mapToDto(userEntity)).thenReturn(expectedUserDto);

    UserDto result = service.login(userLoginRequest);

    assertNotNull(result);
    assertEquals(expectedUserDto, result);
    assertEquals("sample-jwt-token-123", result.getToken().getToken());

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository).findByUsername(userLoginRequest.username());
    verify(tokenRepository).findAllValidTokenByUser(userEntity.getId());
    verify(jwtService).generateToken(userEntity);
    verify(tokenRepository, times(1)).save(any(TokenEntity.class));
  }

  @Test
  void invalidLogin() {
    UserLoginRequest userLoginRequest = new UserLoginRequest("nonexistentUser", "password");

    when(userRepository.findByUsername(userLoginRequest.username())).thenReturn(Optional.empty());

    verify(authenticationManager, never())
        .authenticate(any(UsernamePasswordAuthenticationToken.class));

    assertThrows(EntityNotFoundException.class, () -> service.login(userLoginRequest));

    verify(tokenRepository, never()).save(any(TokenEntity.class));
  }
}

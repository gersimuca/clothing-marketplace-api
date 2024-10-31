package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.Role;
import com.gersimuca.cm.common.exception.EntityAlreadyExistsException;
import com.gersimuca.cm.common.exception.EntityNotFoundException;
import com.gersimuca.cm.common.util.LoggerUtil;
import com.gersimuca.cm.configuration.JwtService;
import com.gersimuca.cm.feature.token.TokenEntity;
import com.gersimuca.cm.feature.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper mapper;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;

  public UserDto createUser(final UserCreateRequest userCreateRequest) {

    final String username = userCreateRequest.getUsername();
    final String password = userCreateRequest.getPassword();
    final String fullName = userCreateRequest.getFullname();

    if (existsByUsername(username)) {
      throw new EntityAlreadyExistsException(UserEntity.class, username);
    }

    UserEntity userEntity =
        UserEntity.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
                .fullName(fullName)
            .role(Role.CLIENT)
            .build();

    userEntity = userRepository.save(userEntity);

    LoggerUtil.info(log, "Database user created: {}", username);

    String jwtToken = jwtService.generateToken(userEntity);

    TokenEntity token =
        TokenEntity.builder()
            .user(userEntity)
            .token(jwtToken)
            .revoked(false)
            .expired(false)
            .build();
    token = tokenRepository.save(token);

    LoggerUtil.info(log, "Token  created {} by user {}", token.getToken(), token.getUser());

    return mapper.mapToDto(userEntity);
  }

  public UserDto getUser(final UserLoginRequest userLoginRequest) {
    final String username = userLoginRequest.username();
    final UserEntity userEntity =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, username));
    return mapper.mapToDto(userEntity);
  }

  private boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}

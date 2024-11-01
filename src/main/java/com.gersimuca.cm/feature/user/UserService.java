package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.Role;
import com.gersimuca.cm.common.exception.EntityAlreadyExistsException;
import com.gersimuca.cm.common.exception.EntityNotFoundException;
import com.gersimuca.cm.common.util.LoggerUtil;
import com.gersimuca.cm.configuration.JwtService;
import com.gersimuca.cm.feature.token.TokenDto;
import com.gersimuca.cm.feature.token.TokenEntity;
import com.gersimuca.cm.feature.token.TokenMapper;
import com.gersimuca.cm.feature.token.TokenRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper mapper;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenMapper tokenMapper;

  @Transactional
  public UserDto createUser(final UserCreateRequest userCreateRequest) {

    final String username = userCreateRequest.getUsername();
    final String password = userCreateRequest.getPassword();
    final String fullName = userCreateRequest.getFullname();
    final String address = userCreateRequest.getAddress();

    if (existsByUsername(username)) {
      throw new EntityAlreadyExistsException(UserEntity.class, username);
    }

    UserEntity userEntity =
        UserEntity.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .fullName(fullName)
            .role(Role.CLIENT)
                .address(address)
            .build();

    userEntity = userRepository.save(userEntity);

    LoggerUtil.info(log, "Database user created: {}", userEntity.getId());

    String jwtToken = jwtService.generateToken(userEntity);

    TokenEntity token =
        TokenEntity.builder()
            .user(userEntity)
            .token(jwtToken)
            .revoked(false)
            .expired(false)
            .build();

    token = tokenRepository.save(token);

    LoggerUtil.info(log, "User {} assign Token", token.getUser().getId());

    UserDto userDto = mapper.mapToDto(userEntity);
    TokenDto tokenDto = tokenMapper.mapToDto(token);
    userDto.setToken(tokenDto);
    return userDto;
  }

  @Transactional
  public UserDto login(final UserLoginRequest userLoginRequest) {
    final String username = userLoginRequest.username();
    final String password = userLoginRequest.password();

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));


    final UserEntity userEntity =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, username));
    List<TokenEntity> validUserToken = tokenRepository.findAllValidTokenByUser(userEntity.getId());
    revokeAllUserToken(validUserToken);
    tokenRepository.saveAll(validUserToken);

    LoggerUtil.info(log, "User {} revoking all previews tokens {}", userEntity.getId(), validUserToken.size());

    String jwtToken = jwtService.generateToken(userEntity);
    final TokenEntity token =
        TokenEntity.builder()
            .user(userEntity)
            .token(jwtToken)
            .revoked(false)
            .expired(false)
            .build();
    tokenRepository.save(token);

    LoggerUtil.info(log, "User {} assign Token", token.getUser().getId());

    UserDto userDto = mapper.mapToDto(userEntity);
    userDto.setToken(tokenMapper.mapToDto(token));

    return userDto;
  }

  private boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  private void revokeAllUserToken(List<TokenEntity> validUserTokens) {
    if (validUserTokens.isEmpty()) return;

    validUserTokens.forEach(
        token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
  }
}

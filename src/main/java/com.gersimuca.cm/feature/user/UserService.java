package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.exception.EntityAlreadyExistsException;
import com.gersimuca.cm.common.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDto createUser(final UserCreateRequest userCreateRequest) {

        final String username = userCreateRequest.username();
        if (existsByUsername(username)) {
            throw new EntityAlreadyExistsException(UserEntity.class, username);
        }

        final UserEntity userEntity =
                UserEntity.builder()
                        .username(userCreateRequest.username())
                        .password(userCreateRequest.password())
                        .build();

        LoggerUtil.info(log, "Database user created: {}", username);

        return mapper.mapToDto(userEntity);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}

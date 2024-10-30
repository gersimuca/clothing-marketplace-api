package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.MapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto mapToDto(UserEntity user);
}

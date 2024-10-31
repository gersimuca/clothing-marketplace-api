package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
  @Mapping(target = "fullname", ignore = true)
  UserDto mapToDto(UserEntity user);
}

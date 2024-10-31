package com.gersimuca.cm.feature.token;

import com.gersimuca.cm.common.MapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TokenMapper {
  TokenDto mapToDto(TokenEntity token);
}

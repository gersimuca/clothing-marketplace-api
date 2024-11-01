package com.gersimuca.cm.feature.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {
  private UserMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = Mappers.getMapper(UserMapper.class);
  }

  @Test
  void mapToDtoEntity() {
    var dto = mapper.mapToDto(UserTestData.JOHN_SMITH_ENTITY);
    assertThat(dto.getId()).isEqualTo(UserTestData.JOHN_SMITH_ENTITY.getId());
    assertThat(dto.getAddress()).isEqualTo(UserTestData.JOHN_SMITH_ENTITY.getAddress());
    assertThat(dto.getUsername()).isEqualTo(UserTestData.JOHN_SMITH_ENTITY.getUsername());
  }
}

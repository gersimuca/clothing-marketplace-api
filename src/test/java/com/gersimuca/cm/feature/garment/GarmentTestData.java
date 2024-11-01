package com.gersimuca.cm.feature.garment;

import com.gersimuca.cm.feature.grament.GarmentDto;
import com.gersimuca.cm.feature.grament.GarmentEntity;
import com.gersimuca.cm.feature.grament.GarmentSize;
import com.gersimuca.cm.feature.grament.GarmentType;
import com.gersimuca.cm.feature.user.UserTestData;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GarmentTestData {
  public static GarmentDto createGarmentDto() {
    return GarmentDto.builder()
        .type(GarmentType.SHIRT)
        .description("A stylish blue shirt.")
        .size(GarmentSize.M)
        .price(BigDecimal.valueOf(25.00))
        .build();
  }

  public static GarmentDto updatedGarmentDto() {
    return GarmentDto.builder()
        .type(GarmentType.SHIRT)
        .description("Updated garment description")
        .size(GarmentSize.M)
        .price(BigDecimal.valueOf(30.00))
        .build();
  }

  public static GarmentEntity createGarmentEntity() {
    return GarmentEntity.builder()
        .id(1L)
        .type(GarmentType.SHIRT)
        .description("A stylish blue shirt.")
        .size(GarmentSize.M)
        .price(BigDecimal.valueOf(25.00))
        .publisher(UserTestData.JOHN_SMITH_ENTITY)
        .build();
  }

  public static GarmentEntity updatedGarmentEntity() {
    return GarmentEntity.builder()
        .id(1L)
        .type(GarmentType.SHIRT)
        .description("Updated garment description")
        .size(GarmentSize.M)
        .price(BigDecimal.valueOf(30.00))
        .publisher(UserTestData.JOHN_SMITH_ENTITY)
        .build();
  }
}

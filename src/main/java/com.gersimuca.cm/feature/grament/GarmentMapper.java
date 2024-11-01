package com.gersimuca.cm.feature.grament;

import com.gersimuca.cm.common.MapperConfig;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface GarmentMapper {
  GarmentDto mapToDto(GarmentEntity garment);

  List<GarmentDto> mapListToDto(List<GarmentEntity> garments);

  GarmentEntity mapToEntity(GarmentDto dto);
}

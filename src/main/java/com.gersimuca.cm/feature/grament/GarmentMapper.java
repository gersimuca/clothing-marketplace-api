package com.gersimuca.cm.feature.grament;


import com.gersimuca.cm.common.MapperConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface GarmentMapper {
    GarmentDto mapToDto(GarmentEntity garment);
    List<GarmentDto> mapListToDto(List<GarmentEntity> garments);
    GarmentEntity mapToEntity(GarmentDto dto);
}

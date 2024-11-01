package com.gersimuca.cm.feature.grament;

import com.gersimuca.cm.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GarmentRepository extends BaseRepository<GarmentEntity, Long> {
    List<GarmentEntity> findByTypeAndSizeAndPriceBetween(GarmentType type, GarmentSize size, BigDecimal minPrice, BigDecimal maxPrice);
}

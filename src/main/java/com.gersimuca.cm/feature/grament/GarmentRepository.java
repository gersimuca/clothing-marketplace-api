package com.gersimuca.cm.feature.grament;

import com.gersimuca.cm.common.repository.BaseRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface GarmentRepository extends BaseRepository<GarmentEntity, Long> {
  List<GarmentEntity> findByTypeAndSizeAndPriceBetween(
      GarmentType type, GarmentSize size, BigDecimal minPrice, BigDecimal maxPrice);
}

package com.gersimuca.cm.feature.grament;

import com.gersimuca.cm.common.exception.EntityNotFoundException;
import com.gersimuca.cm.feature.user.UserEntity;
import com.gersimuca.cm.feature.user.UserRepository;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GarmentService {
  private final GarmentRepository garmentRepository;
  private final GarmentMapper garmentMapper;
  private final UserRepository userRepository;

  public List<GarmentDto> findAll(
      final GarmentType type,
      final GarmentSize size,
      final BigDecimal minPrice,
      final BigDecimal maxPrice) {
    return garmentRepository
        .findAll(
            (root, query, criteriaBuilder) -> {
              List<Predicate> predicates = new ArrayList<>();

              if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
              }
              if (size != null) {
                predicates.add(criteriaBuilder.equal(root.get("size"), size));
              }
              if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
              }
              if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
              }

              return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            })
        .stream()
        .map(garmentMapper::mapToDto)
        .collect(Collectors.toList());
  }

  public GarmentDto publishGarment(final GarmentDto garmentDto, final UserEntity publisher) {
    final GarmentType type = garmentDto.getType();
    final String description = garmentDto.getDescription();
    final GarmentSize size = garmentDto.getSize();
    final BigDecimal price = garmentDto.getPrice();

    final UserEntity user =
        userRepository
            .findByUsername(publisher.getUsername())
            .orElseThrow(
                () -> new EntityNotFoundException(UserEntity.class, publisher.getUsername()));

    GarmentEntity garment =
        GarmentEntity.builder()
            .type(type)
            .description(description)
            .size(size)
            .price(price)
            .publisher(user)
            .build();

    garment = garmentRepository.save(garment);
    return garmentMapper.mapToDto(garment);
  }

  public GarmentDto updateGarment(
      final Long id, final GarmentDto garmentDto, final UserEntity publisher) {
    final GarmentType type = garmentDto.getType();
    final String description = garmentDto.getDescription();
    final GarmentSize size = garmentDto.getSize();
    final BigDecimal price = garmentDto.getPrice();

    GarmentEntity entity =
        garmentRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(GarmentEntity.class, id));
    if (!entity.getPublisher().getUsername().equals(publisher.getUsername())) {
      throw new AuthenticationException("You can only update your own garments") {
        @Override
        public String getMessage() {
          return super.getMessage();
        }
      };
    }

    entity.setType(type);
    entity.setDescription(description);
    entity.setSize(size);
    entity.setPrice(price);

    garmentRepository.save(entity);
    return garmentMapper.mapToDto(entity);
  }

  public void deleteGarment(final Long id, final UserEntity publisher) {
    GarmentEntity garment =
        garmentRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(GarmentEntity.class, id));
    if (!garment.getPublisher().getUsername().equals(publisher.getUsername())) {
      throw new AuthenticationException("You can only update your own garments") {
        @Override
        public String getMessage() {
          return super.getMessage();
        }
      };
    }
    garmentRepository.delete(garment);
  }
}

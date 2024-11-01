package com.gersimuca.cm.feature.grament;

import com.gersimuca.cm.feature.user.UserEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clothes")
@RequiredArgsConstructor
public class GarmentController {
  private final GarmentService service;

  @GetMapping
  public List<GarmentDto> getAllGarments(
      @RequestParam Optional<GarmentType> type,
      @RequestParam Optional<GarmentSize> size,
      @RequestParam Optional<BigDecimal> minPrice,
      @RequestParam Optional<BigDecimal> maxPrice) {
    return service.findAll(
        type.orElse(null),
        size.orElse(null),
        minPrice.orElse(BigDecimal.ZERO),
        maxPrice.orElse(BigDecimal.valueOf(Long.MAX_VALUE)));
  }

  @PostMapping
  public ResponseEntity<GarmentDto> publishGarment(
      @RequestBody GarmentDto garmentDto, @AuthenticationPrincipal UserEntity publisher) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(service.publishGarment(garmentDto, publisher));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GarmentDto> updateGarment(
      @PathVariable Long id,
      @RequestBody GarmentDto garmentDto,
      @AuthenticationPrincipal UserEntity publisher) {
    return ResponseEntity.ok(service.updateGarment(id, garmentDto, publisher));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGarment(
      @PathVariable Long id, @AuthenticationPrincipal UserEntity publisher) {
    service.deleteGarment(id, publisher);
    return ResponseEntity.noContent().build();
  }
}

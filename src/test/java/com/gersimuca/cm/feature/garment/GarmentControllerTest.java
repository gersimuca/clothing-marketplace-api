package com.gersimuca.cm.feature.garment;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gersimuca.cm.feature.grament.*;
import com.gersimuca.cm.feature.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GarmentControllerTest {
  @Mock private GarmentService service;
  @InjectMocks private GarmentController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new GarmentController(service);
    var dto = GarmentTestData.createGarmentDto();
    when(service.publishGarment(any(GarmentDto.class), any(UserEntity.class))).thenReturn(dto);
  }

  @Test
  void publish() {
    controller.publishGarment(GarmentTestData.createGarmentDto(), UserTestData.JOHN_SMITH_ENTITY);
    verify(service).publishGarment(any(GarmentDto.class), any(UserEntity.class));
  }

  @Test
  void update() {
    controller.updateGarment(
        1L, GarmentTestData.createGarmentDto(), UserTestData.JOHN_SMITH_ENTITY);
    verify(service).updateGarment(anyLong(), any(GarmentDto.class), any(UserEntity.class));
  }

  @Test
  void delete() {
    controller.deleteGarment(1L, UserTestData.JOHN_SMITH_ENTITY);
    verify(service).deleteGarment(anyLong(), any(UserEntity.class));
  }
}

package com.gersimuca.cm.feature.garment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gersimuca.cm.feature.grament.*;
import com.gersimuca.cm.feature.user.UserEntity;
import com.gersimuca.cm.feature.user.UserRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GarmentServiceTest {

  @Autowired private ObjectMapper mapper;

  @Mock private GarmentRepository garmentRepository;

  @Mock private GarmentMapper garmentMapper;

  @Mock private UserRepository userRepository;

  @InjectMocks private GarmentService garmentService;

  private UserEntity publisher;
  private GarmentDto garmentDto;
  private GarmentEntity garmentEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    publisher = new UserEntity();
    publisher.setUsername("johnsmith");

    garmentDto = new GarmentDto();
    garmentDto.setType(GarmentType.SHIRT);
    garmentDto.setDescription("A nice shirt");
    garmentDto.setSize(GarmentSize.M);
    garmentDto.setPrice(BigDecimal.valueOf(25.00));

    garmentEntity = new GarmentEntity();
    garmentEntity.setType(GarmentType.SHIRT);
    garmentEntity.setDescription("A nice shirt");
    garmentEntity.setSize(GarmentSize.M);
    garmentEntity.setPrice(BigDecimal.valueOf(25.00));
    garmentEntity.setPublisher(publisher);
  }

  @Test
  void publishGarment() {
    when(userRepository.findByUsername(publisher.getUsername())).thenReturn(Optional.of(publisher));
    when(garmentMapper.mapToEntity(any(GarmentDto.class))).thenReturn(garmentEntity);
    when(garmentMapper.mapToDto(any(GarmentEntity.class))).thenReturn(garmentDto);
    when(garmentRepository.save(any(GarmentEntity.class))).thenReturn(garmentEntity);

    GarmentDto result = garmentService.publishGarment(garmentDto, publisher);

    verify(userRepository).findByUsername(publisher.getUsername());
    verify(garmentRepository).save(any(GarmentEntity.class));
    verify(garmentMapper).mapToDto(garmentEntity);

    assertEquals(garmentDto.getType(), result.getType());
    assertEquals(garmentDto.getDescription(), result.getDescription());
    assertEquals(garmentDto.getSize(), result.getSize());
    assertEquals(garmentDto.getPrice(), result.getPrice());
  }

  @Test
  void updateGarment() {
    Long garmentId = 1L;
    when(garmentRepository.findById(garmentId)).thenReturn(Optional.of(garmentEntity));
    when(garmentMapper.mapToEntity(any(GarmentDto.class))).thenReturn(garmentEntity);
    when(garmentRepository.save(any(GarmentEntity.class))).thenReturn(garmentEntity);

    GarmentDto updatedDto = new GarmentDto();
    updatedDto.setType(GarmentType.SHIRT);
    updatedDto.setDescription("Updated description");
    updatedDto.setSize(GarmentSize.L);
    updatedDto.setPrice(BigDecimal.valueOf(30.00));

    garmentService.updateGarment(garmentId, updatedDto, publisher);

    verify(garmentRepository).findById(garmentId);
    verify(garmentRepository).save(any(GarmentEntity.class));
    verify(garmentMapper).mapToDto(garmentEntity);
  }

  @Test
  void deleteGarment() {
    Long garmentId = 1L;
    when(garmentRepository.findById(garmentId)).thenReturn(Optional.of(garmentEntity));

    garmentService.deleteGarment(garmentId, publisher);

    verify(garmentRepository).delete(garmentEntity);
  }

  @Test
  void deleteGarmentInvalid() {
    Long garmentId = 1L;
    UserEntity anotherPublisher = new UserEntity();
    anotherPublisher.setUsername("othersmith");

    when(garmentRepository.findById(garmentId)).thenReturn(Optional.of(garmentEntity));

    AuthenticationException thrown =
        assertThrows(
            AuthenticationException.class,
            () -> {
              garmentService.deleteGarment(garmentId, anotherPublisher);
            });

    assertEquals("You can only update your own garments", thrown.getMessage());
  }
}

package com.gersimuca.cm.feature.grament;

import com.gersimuca.cm.feature.user.UserEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "garment")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GarmentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GarmentType type;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GarmentSize size;

  @Column(nullable = false)
  private BigDecimal price;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "publisher_id", nullable = false)
  private UserEntity publisher;

  @Builder.Default
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt = LocalDateTime.now();

  @PreUpdate
  public void setLastUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}

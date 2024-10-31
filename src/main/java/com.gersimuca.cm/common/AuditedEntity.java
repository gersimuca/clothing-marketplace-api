package com.gersimuca.cm.common;

import com.gersimuca.cm.feature.user.UserEntity;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AuditedEntity {
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @ManyToOne
  @CreatedBy
  @JoinColumn(name = "created_user_id", nullable = false, updatable = false)
  private UserEntity createdBy;

  @UpdateTimestamp
  @Column(name = "last_updated_at", nullable = false)
  private OffsetDateTime lastUpdatedAt;

  @ManyToOne
  @LastModifiedBy
  @JoinColumn(name = "last_updated_user_id", nullable = false)
  private UserEntity lastModifiedBy;
}

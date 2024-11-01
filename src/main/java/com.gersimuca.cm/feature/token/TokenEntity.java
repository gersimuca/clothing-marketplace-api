package com.gersimuca.cm.feature.token;

import com.gersimuca.cm.feature.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "token")
public class TokenEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "token")
  private String token;

  @Column(name = "token_type")
  private String tokenType = "BEARER";

  @Column(name = "expired")
  private boolean expired;

  @Column(name = "revoked")
  private boolean revoked;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}

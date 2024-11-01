package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.common.Role;
import com.gersimuca.cm.feature.grament.GarmentEntity;
import com.gersimuca.cm.feature.token.TokenEntity;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column private String address;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<TokenEntity> tokens;

  @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
  private List<GarmentEntity> garments;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Role.getAuthoritiesForRole(role);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

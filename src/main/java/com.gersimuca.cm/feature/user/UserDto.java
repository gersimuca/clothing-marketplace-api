package com.gersimuca.cm.feature.user;

import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
  private Long id;
  private String username;
  private String fullname;
  private String address;
  private String token;
}

package com.gersimuca.cm.feature.user;

import com.gersimuca.cm.feature.token.TokenDto;
import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class UserDto {
  private Long id;
  private String username;
  private String fullname;
  private String address;
  private TokenDto token;
}

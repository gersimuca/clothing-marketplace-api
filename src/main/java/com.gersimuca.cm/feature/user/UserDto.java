package com.gersimuca.cm.feature.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gersimuca.cm.feature.token.TokenDto;
import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDto {
  private Long id;
  private String username;
  private String fullname;
  private String address;
  private TokenDto token;
}

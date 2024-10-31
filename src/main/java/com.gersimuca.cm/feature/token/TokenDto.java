package com.gersimuca.cm.feature.token;

import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class TokenDto {
  private String token;
}

package com.gersimuca.cm.feature.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TokenDto {
  private String token;
}

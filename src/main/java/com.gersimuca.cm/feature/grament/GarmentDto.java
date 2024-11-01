package com.gersimuca.cm.feature.grament;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GarmentDto {
    private Long id;
    private GarmentType type;
    private String description;
    private GarmentSize size;
    private BigDecimal price;
}

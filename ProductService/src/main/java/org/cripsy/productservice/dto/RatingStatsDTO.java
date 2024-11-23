package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingStatsDTO {
    private Long rating5;
    private Long rating4;
    private Long rating3;
    private Long rating2;
    private Long rating1;
}

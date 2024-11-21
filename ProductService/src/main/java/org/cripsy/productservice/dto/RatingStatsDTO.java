package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingStatsDTO {
    private int rating1;
    private int rating2;
    private int rating3;
    private int rating4;
    private int rating5;
}

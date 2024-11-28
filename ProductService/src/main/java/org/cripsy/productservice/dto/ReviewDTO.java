package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private String user;
    private int rating;
    private String comment;
    private LocalDate ratedDate;
}


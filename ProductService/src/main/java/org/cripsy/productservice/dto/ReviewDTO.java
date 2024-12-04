package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private String userName;
    private int rating;
    private String comment;
    private ZonedDateTime ratedDate;
}


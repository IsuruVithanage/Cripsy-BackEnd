package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {

    @EmbeddedId
    private RatingId id;
    private String comment;
    private int rating;
    private ZonedDateTime ratedDate;
}
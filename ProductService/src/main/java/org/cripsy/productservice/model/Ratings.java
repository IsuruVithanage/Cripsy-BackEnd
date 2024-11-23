package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Clock;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {

    @EmbeddedId
    private RatingId id;
    private String comment;
    private int rating;
    private LocalDate ratedDate;

    public void setUser(String user){
        this.id.setUser(user);
    }


    @PrePersist
    public void onPrePersist() {
        if(this.comment != null) {
            this.ratedDate = LocalDate.now(Clock.systemUTC());
        }
    }
}
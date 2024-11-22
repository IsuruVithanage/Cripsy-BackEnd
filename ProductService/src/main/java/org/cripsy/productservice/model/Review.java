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
public class Review {


    @EmbeddedId
    private ReviewKey id;

    private String comment;
    private int rating;
    private LocalDate ratedDate;

    public void setUser(String user){
        this.id.setUser(user);
    }

    public void setComment(String comment){
        if(comment != null && !comment.trim().isEmpty()){
            this.comment = comment.trim();
        }
    }

    @PrePersist
    public void onPrePersist() {
        if(this.comment != null) {
            this.ratedDate = LocalDate.now(Clock.systemUTC());
        }
    }
}
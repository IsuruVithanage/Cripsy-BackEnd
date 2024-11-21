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
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Id
    @Column(name = "\"user\"")
    private String user;
    private String comment;
    private int rating;
    private LocalDate ratedDate;

    @PrePersist
    public void onPrePersist() {
        this.ratedDate = LocalDate.now(Clock.systemUTC());
    }
}

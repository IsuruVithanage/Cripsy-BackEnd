package org.cripsy.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
    @Id
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Min(0)
    @Column(nullable = false, columnDefinition = "int default 0")
    private int rating1;

    @Min(0)
    @Column(nullable = false, columnDefinition = "int default 0")
    private int rating2;

    @Min(0)
    @Column(nullable = false, columnDefinition = "int default 0")
    private int rating3;

    @Min(0)
    @Column(nullable = false, columnDefinition = "int default 0")
    private int rating4;

    @Min(0)
    @Column(nullable = false, columnDefinition = "int default 0")
    private int rating5;
}

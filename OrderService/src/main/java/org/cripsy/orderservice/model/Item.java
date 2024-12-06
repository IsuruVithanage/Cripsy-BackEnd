package org.cripsy.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;
    private Integer quantity;
    private Double price;
    private Double discount;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)

   

    private Order order; // Foreign key linking to the Order entity
}

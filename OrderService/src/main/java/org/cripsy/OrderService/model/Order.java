package org.cripsy.OrderService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer orderId;

    private Integer customerID;
    private LocalDate purchasedDate;
    private LocalDate deliveredDate;
    private String orderStatus;
    private Double totalPrice;

    private Integer deliveryPersonId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items; // New relationship with the Item entity
}

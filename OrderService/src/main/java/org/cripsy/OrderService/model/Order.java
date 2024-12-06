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

    @ElementCollection
    private List<Integer> itemList; // Define as appropriate if using item IDs or a different structure

    private Integer deliveryPersonId;

    // Custom setStatus method
    public void setStatus(String status) {
        this.orderStatus = status;
    }
}

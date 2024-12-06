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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer deliveryPersonId;
    private Integer customerID;
    private LocalDate purchasedDate;
    private LocalDate deliveredDate;
    private String orderStatus;
    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    @PrePersist
    public void prePersist() {
        this.purchasedDate = LocalDate.now();
        this.orderStatus = "Placed";

        if (items != null && !items.isEmpty()) {
            this.totalPrice = items.stream()
                    .mapToDouble(item -> {
                        double discountedPrice = item.getPrice() * (1 - (item.getDiscount() / 100));
                        return discountedPrice * item.getQuantity();
                    })
                    .sum();
        } else {
            this.totalPrice = 0.0;
        }
    }
}

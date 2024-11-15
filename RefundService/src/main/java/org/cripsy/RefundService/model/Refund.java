package org.cripsy.RefundService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "refunds")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer refundId;

    private Integer orderId;
    private LocalDate refundRequestDate;
    private String refundState;
}

package org.cripsy.orderservice.repository;

import org.cripsy.orderservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(UUID orderId);
}

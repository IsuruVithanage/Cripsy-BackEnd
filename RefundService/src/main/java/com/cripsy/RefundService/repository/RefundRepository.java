package org.cripsy.OrderService.repository;

import org.cripsy.OrderService.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
}

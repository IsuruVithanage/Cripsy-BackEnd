package org.cripsy.RefundService.repository;

import org.cripsy.RefundService.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
}

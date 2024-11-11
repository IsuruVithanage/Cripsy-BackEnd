package org.cripsy.customerservice.repository;

import org.cripsy.customerservice.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<customer, Long> {
}

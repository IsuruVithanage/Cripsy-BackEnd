package org.cripsy.OrderService.repository;

import org.cripsy.OrderService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

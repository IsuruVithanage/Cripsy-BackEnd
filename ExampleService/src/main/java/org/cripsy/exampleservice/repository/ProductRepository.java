package org.cripsy.exampleservice.repository;

import org.cripsy.exampleservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

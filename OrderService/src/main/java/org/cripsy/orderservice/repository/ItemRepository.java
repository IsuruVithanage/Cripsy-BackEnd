package org.cripsy.orderservice.repository;

import org.cripsy.orderservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}

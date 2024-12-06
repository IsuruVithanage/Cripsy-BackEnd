package org.cripsy.OrderService.repository;

import org.cripsy.OrderService.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}

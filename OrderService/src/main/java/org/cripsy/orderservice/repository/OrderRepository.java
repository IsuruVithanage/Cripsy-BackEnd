package org.cripsy.orderservice.repository;

import org.cripsy.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Double getTotalSumOfTotalPrice();

}

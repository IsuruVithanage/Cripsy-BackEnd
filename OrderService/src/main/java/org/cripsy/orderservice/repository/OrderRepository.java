package org.cripsy.orderservice.repository;

import org.cripsy.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Query to get the total sum of totalPrice
    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Double getTotalSumOfTotalPrice();

    List<Order> findByOrderStatus(String orderStatus);


    // Query to get the monthly sum of totalPrice
    @Query("SELECT EXTRACT(YEAR FROM o.purchasedDate) AS year, " +
            "EXTRACT(MONTH FROM o.purchasedDate) AS month, " +
            "SUM(o.totalPrice) AS total " +
            "FROM Order o " +
            "GROUP BY EXTRACT(YEAR FROM o.purchasedDate), EXTRACT(MONTH FROM o.purchasedDate) " +
            "ORDER BY year, month")
    List<Object[]> getMonthlyTotalSumOfTotalPrice();
}

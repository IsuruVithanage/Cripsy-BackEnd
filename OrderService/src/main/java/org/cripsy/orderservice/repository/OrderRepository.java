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

    //Query to get  this month total sum of totalPrice
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE)")
    Double getTotalPriceForCurrentMonth();

    //Query to get  last month total sum of totalPrice
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE (YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE) - 1) OR (YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) - 1 AND MONTH(o.purchasedDate) = 12)")
    Double getTotalPriceForLastMonth();


//    // Query to get the monthly sum of totalPrice
//    @Query("SELECT EXTRACT(YEAR FROM o.purchasedDate) AS year, " +
//            "EXTRACT(MONTH FROM o.purchasedDate) AS month, " +
//            "SUM(o.totalPrice) AS total " +
//            "FROM Order o " +
//            "GROUP BY EXTRACT(YEAR FROM o.purchasedDate), EXTRACT(MONTH FROM o.purchasedDate) " +
//            "ORDER BY year, month")
//    List<Object[]> getMonthlyTotalSumOfTotalPrice();

    // Query to get this  monthly total quantity of items
    @Query("SELECT SUM(i.quantity) FROM Order o JOIN o.items i WHERE YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE)")
    Long getTotalItemQuantityForCurrentMonth();

    // Query to get last  monthly total quantity of items
    @Query("SELECT SUM(i.quantity) FROM Order o JOIN o.items i WHERE YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE) - 1")
    Long getTotalItemQuantityForLastMonth();





    // Query for total orders this month
    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE)")
    long getTotalOrdersForCurrentMonth();

    // Query for total orders last month
    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE) - 1")
    long getTotalOrdersForLastMonth();



}

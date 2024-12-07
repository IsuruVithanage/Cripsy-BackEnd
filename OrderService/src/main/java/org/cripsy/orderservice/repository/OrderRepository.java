package org.cripsy.orderservice.repository;

import org.cripsy.orderservice.dto.MonthlyTotalPriceDTO;
import org.cripsy.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
//
//    // Query to get the total sum of totalPrice
//    @Query("SELECT SUM(o.totalPrice) FROM Order o")
//    Double getTotalSumOfTotalPrice();


    //Query to get  this month total sum of totalPrice
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE)")
    Double getTotalPriceForCurrentMonth();

    //Query to get  last month total sum of totalPrice
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE (YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) AND MONTH(o.purchasedDate) = MONTH(CURRENT_DATE) - 1) OR (YEAR(o.purchasedDate) = YEAR(CURRENT_DATE) - 1 AND MONTH(o.purchasedDate) = 12)")
    Double getTotalPriceForLastMonth();


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


    @Query("SELECT new org.cripsy.orderservice.dto.MonthlyTotalPriceDTO( " +
            "EXTRACT(YEAR FROM o.purchasedDate), " +
            "EXTRACT(MONTH FROM o.purchasedDate), " +
            "SUM(o.totalPrice)) " +
            "FROM Order o " +
            "GROUP BY EXTRACT(YEAR FROM o.purchasedDate), EXTRACT(MONTH FROM o.purchasedDate) " +
            "ORDER BY EXTRACT(YEAR FROM o.purchasedDate), EXTRACT(MONTH FROM o.purchasedDate)")
    List<MonthlyTotalPriceDTO> findMonthlyTotalPrices();

    List<Order> findByOrderStatus(String orderStatus);

    List<Order> findOrderByCustomerID(Integer customerID);

    List<Order> findOrderByDeliveryPersonId(Integer deliveryPersonId);

    // Update the order status by order ID
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
    int updateOrderStatus(@Param("orderId") Integer orderId, @Param("orderStatus") String orderStatus);




}
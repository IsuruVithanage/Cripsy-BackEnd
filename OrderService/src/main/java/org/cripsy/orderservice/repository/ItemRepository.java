package org.cripsy.orderservice.repository;

import org.cripsy.orderservice.dto.BestSellingProductDTO;
import org.cripsy.orderservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT new org.cripsy.orderservice.dto.BestSellingProductDTO(" +
            "i.productId, SUM(i.quantity), SUM(i.price * i.quantity), SUM((i.price - i.discount) * i.quantity)) " +
            "FROM Item i " +
            "GROUP BY i.productId " +
            "ORDER BY SUM(i.quantity) DESC " +
            "LIMIT 5")
    List<BestSellingProductDTO> findBestSellingProducts();
}

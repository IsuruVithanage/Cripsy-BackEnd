package org.cripsy.productservice.repository;

import org.cripsy.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional // Ensures the operation is transactional
    @Query("UPDATE Product p " +
            "SET p.rating = ((p.rating * p.ratingCount) + :newRating) / (p.ratingCount + 1), " +
            "    p.ratingCount = p.ratingCount + 1 " +
            "WHERE p.productId = :productId")
    int updateProductRating(@Param("productId") int productId,
                            @Param("newRating") int newRating);
}

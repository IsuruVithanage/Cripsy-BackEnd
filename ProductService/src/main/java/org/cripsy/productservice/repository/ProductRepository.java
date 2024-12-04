package org.cripsy.productservice.repository;

import org.cripsy.productservice.dto.ProductItemDTO;
import org.cripsy.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
        SELECT new org.cripsy.productservice.dto.ProductItemDTO(
            p.productId,
            p.name,
            p.price,
            p.description,
            p.stock,
            p.discount,
            p.avgRatings,
            p.ratingCount,
            (SELECT COUNT(r) FROM Ratings r WHERE r.id.product = p AND r.comment IS NOT NULL),
            (CASE WHEN EXISTS (SELECT r FROM Ratings r WHERE r.id.product = p AND r.id.userId = :userId) THEN true ELSE false END),
            (CASE WHEN EXISTS (SELECT w FROM Watchlist w WHERE w.id.product = p AND w.id.userId = :userId) THEN true ELSE false END),
            new org.cripsy.productservice.dto.RatingStatsDTO(
                (SELECT COUNT(r) FROM Ratings r WHERE r.id.product = p AND r.rating = 5),
                (SELECT COUNT(r) FROM Ratings r WHERE r.id.product = p AND r.rating = 4),
                (SELECT COUNT(r) FROM Ratings r WHERE r.id.product = p AND r.rating = 3),
                (SELECT COUNT(r) FROM Ratings r WHERE r.id.product = p AND r.rating = 2),
                (SELECT COUNT(r) FROM Ratings r WHERE r.id.product = p AND r.rating = 1)
            ),
            null,
            null
        )
        FROM Product p
        WHERE p.productId = :productId
    """)
    Optional<ProductItemDTO> findProductItemDetails(
            @Param("productId") int productId,
            @Param("userId") int userId
    );


    @Query("""
        SELECT i.url
        FROM ImageUrls i
        WHERE i.product.productId = :productId
    """)
    List<String> findImageUrls(@Param("productId") int productId);


    @Transactional
    @Modifying
    @Query(value = """
        UPDATE product
        SET stock = stock - :quantity
        WHERE product_id = :productId AND stock >= :quantity
    """, nativeQuery = true)
    int decrementStock(
        @Param("productId") Integer productId,
        @Param("quantity") Integer quantity
    );


    @Transactional
    @Modifying
    @Query(value = """
        UPDATE product
        SET stock = stock + :quantity
        WHERE product_id = :productId
    """, nativeQuery = true)
    void incrementStock(
        @Param("productId") Integer productId,
        @Param("quantity") Integer quantity
    );
}

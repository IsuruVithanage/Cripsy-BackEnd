package org.cripsy.productservice.repository;

import org.cripsy.productservice.dto.AddToCartDTO;
import org.cripsy.productservice.dto.CartItemDTO;
import org.cripsy.productservice.model.Cart;
import org.cripsy.productservice.model.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    @Query("""
        SELECT new  org.cripsy.productservice.dto.CartItemDTO(
           c.id.product.productId,
           c.id.product.name,
           c.id.product.description,
           c.id.product.price,
           c.id.product.discount,
           c.id.product.stock,
           c.id.product.ratingCount,
           c.id.product.avgRatings,
           c.quantity,
           c.quantity * c.id.product.price * (1 - c.id.product.discount / 100),
           (
                SELECT COUNT(r)
                FROM Ratings r
                WHERE r.id.product.productId = c.id.product.productId
                AND r.comment IS NOT NULL
           ),
           (
                SELECT i.url
                FROM ImageUrls i
                WHERE i.product.productId = c.id.product.productId
                ORDER BY i.url LIMIT 1
           )
       )
       FROM Cart c
       WHERE c.id.userId = :userId
    """)
    List<CartItemDTO> findByUserId(@Param("userId") Integer userId);


    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO cart (product_id, user_id, quantity)
        VALUES (
            :#{#addToCartDTO.productId},
            :#{#addToCartDTO.userId},
            :#{#addToCartDTO.quantity}
        )
        ON CONFLICT (product_id, "user_id")
        DO UPDATE
        SET quantity = EXCLUDED.quantity
    """, nativeQuery = true)
    void upsertCart( @Param("addToCartDTO") AddToCartDTO addToCartDTO );

    @Transactional
    @Modifying
    @Query("""
        DELETE FROM Cart c
        WHERE c.id.product.productId = :productId
        AND c.id.userId = :userId
    """)
    void removeFromCart(@Param("productId") Integer productId, @Param("userId") Integer userId);

}

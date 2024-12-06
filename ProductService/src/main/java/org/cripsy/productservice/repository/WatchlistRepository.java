package org.cripsy.productservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.model.WatchlistId;
import org.cripsy.productservice.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, WatchlistId> {
    @Query("""
        SELECT new org.cripsy.productservice.dto.ProductCardDTO(
            p.productId,
            p.name,
            p.price,
            p.description,
            p.ratingCount,
            p.avgRatings,
            (SELECT i.url FROM ImageUrls i WHERE i.product = p ORDER BY i.url LIMIT 1)
        )
        FROM Watchlist w
        JOIN w.id.product p
        WHERE w.id.userId = :userId
        GROUP BY p.productId, p.name, p.price, p.description, p.ratingCount, p.avgRatings
    """)
    List<ProductCardDTO> findAllByUserId(@Param("userId") Integer userId);


    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO watchlist (product_id, user_id)
        VALUES ( :productId, :userId )
    """, nativeQuery = true)
    void addToWatchlist(
        @Param("productId") Integer productId,
        @Param("userId") Integer userId
    );


    @Transactional
    @Modifying
    @Query("""
        DELETE FROM Watchlist w
        WHERE w.id.product.productId = :productId
        AND w.id.userId = :userId
    """)
    void removeFromWatchlist(
        @Param("productId") Integer productId,
        @Param("userId") Integer userId
    );
}

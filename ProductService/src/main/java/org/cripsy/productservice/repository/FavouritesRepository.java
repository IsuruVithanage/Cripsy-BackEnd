package org.cripsy.productservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.model.FavouriteId;
import org.cripsy.productservice.model.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavouritesRepository  extends JpaRepository<Favourites, FavouriteId> {
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
        FROM Favourites f
        JOIN f.id.product p
        WHERE f.id.userId = :userId
        GROUP BY p.productId, p.name, p.price, p.description, p.ratingCount, p.avgRatings
    """)
    List<ProductCardDTO> findAllByUserId(@Param("userId") Integer userId);


    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO favourites (product_id, user_id)
        VALUES ( :productId, :userId )
    """, nativeQuery = true)
    void addToFavourites(
        @Param("productId") Integer productId,
        @Param("userId") Integer userId
    );


    @Transactional
    @Modifying
    @Query("""
        DELETE FROM Favourites f
        WHERE f.id.product.productId = :productId
        AND f.id.userId = :userId
    """)
    void removeFavourite(
        @Param("productId") Integer productId,
        @Param("userId") Integer userId
    );
}

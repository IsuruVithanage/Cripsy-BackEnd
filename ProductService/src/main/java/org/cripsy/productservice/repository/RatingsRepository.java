package org.cripsy.productservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.productservice.dto.RateProductDTO;
import org.cripsy.productservice.dto.ReviewDTO;
import org.cripsy.productservice.model.Ratings;
import org.cripsy.productservice.model.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
public interface RatingsRepository extends JpaRepository<Ratings, RatingId>{

    @Query(value = """
        SELECT new org.cripsy.productservice.dto.ReviewDTO(
            r.id.user, r.rating, r.comment, r.ratedDate
        )
        FROM Ratings r
        WHERE r.id.product.productId = :productId AND r.comment IS NOT NULL
        ORDER BY r.id.user
    """)
    List<ReviewDTO> findReviewsByProductId(
            @Param("productId") int productId,
            Pageable pageable
    );


    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO ratings (product_id, "user", comment, rating, rated_date)
        VALUES (
            :#{#rateProductDTO.productId},
            :#{#rateProductDTO.user},
            :#{#rateProductDTO.comment},
            :#{#rateProductDTO.rating},
            CASE
                WHEN COALESCE(:#{#rateProductDTO.comment}, '') <> ''
                THEN CURRENT_DATE
                ELSE NULL
            END
        )
        ON CONFLICT (product_id, "user")
        DO UPDATE
        SET comment = EXCLUDED.comment,
            rating = EXCLUDED.rating,
            rated_date = EXCLUDED.rated_date
    """, nativeQuery = true)
    void saveRating(@Param("rateProductDTO") RateProductDTO rateProductDTO);
}
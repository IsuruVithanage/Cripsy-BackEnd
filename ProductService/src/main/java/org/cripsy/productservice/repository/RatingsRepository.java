package org.cripsy.productservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.productservice.dto.RatingStatsDTO;
import org.cripsy.productservice.dto.ReviewDTO;
import org.cripsy.productservice.model.Ratings;
import org.cripsy.productservice.model.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface RatingsRepository extends JpaRepository<Ratings, RatingId>{
    @Query("""
        SELECT new org.cripsy.productservice.dto.RatingStatsDTO(
            COUNT(CASE WHEN r.rating = 5 THEN 1 ELSE null END),
            COUNT(CASE WHEN r.rating = 4 THEN 1 ELSE null END),
            COUNT(CASE WHEN r.rating = 3 THEN 1 ELSE null END),
            COUNT(CASE WHEN r.rating = 2 THEN 1 ELSE null END),
            COUNT(CASE WHEN r.rating = 1 THEN 1 ELSE null END)
        )
        FROM Ratings r
        WHERE r.id.productId.productId = :productId
    """)
    RatingStatsDTO getRatingsSummary(@Param("productId") int productId);


    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO ratings (product_id, "user", comment, rating, rated_date)
        VALUES (
            :#{#reviewDTO.productId}, 
            :#{#reviewDTO.user}, 
            :#{#reviewDTO.comment}, 
            :#{#reviewDTO.rating}, 
            CASE
                WHEN COALESCE(:#{#reviewDTO.comment}, '') <> ''
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
    void saveRating(@Param("reviewDTO") ReviewDTO reviewDTO);
}
package org.cripsy.productservice.repository;

import jakarta.transaction.Transactional;
import org.cripsy.productservice.dto.AddReviewDTO;
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
            r.userName, r.rating, r.comment, r.ratedDate
        )
        FROM Ratings r
        WHERE r.id.product.productId = :productId AND r.comment IS NOT NULL
        ORDER BY r.ratedDate DESC, r.userName
    """)
    List<ReviewDTO> findReviewsByProductId(
            @Param("productId") int productId,
            Pageable pageable
    );


    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO ratings (product_id, user_id, comment, rating, user_name, rated_date)
        VALUES (
            :#{#addReviewDTO.productId},
            :#{#addReviewDTO.userId},
            :#{#addReviewDTO.comment},
            :#{#addReviewDTO.rating},
            CASE
                WHEN COALESCE(:#{#addReviewDTO.comment}, '') <> ''
                THEN :#{#addReviewDTO.userName}
            END,
            CASE
                WHEN COALESCE(:#{#addReviewDTO.comment}, '') <> ''
                THEN CURRENT_TIMESTAMP AT TIME ZONE 'UTC'
            END
        )
    """, nativeQuery = true)
    void saveRating(@Param("addReviewDTO") AddReviewDTO addReviewDTO);
}
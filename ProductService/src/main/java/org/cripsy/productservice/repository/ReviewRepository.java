package org.cripsy.productservice.repository;

import org.cripsy.productservice.model.Review;
import org.cripsy.productservice.model.ReviewKey;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, ReviewKey>{
}

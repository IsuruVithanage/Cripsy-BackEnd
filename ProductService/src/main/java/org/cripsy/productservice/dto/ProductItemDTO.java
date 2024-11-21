package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDTO {
    private Integer productId;
    private String name;
    private Double price;
    private String description;
    private Double overallRatings;
    private Integer ratingCount;
    private Integer totalReviewsCount;
    private Integer stock;
    private Double discount;
    private boolean isAddedToFavorites;
    private List<String> imageUrls;
    private List<RatingStatsDTO> ratings;
    private List<ReviewDTO> reviews;
}

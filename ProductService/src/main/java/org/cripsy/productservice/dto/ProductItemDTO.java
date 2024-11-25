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
    private Integer stock;
    private Double discount;
    private Boolean isAddedToFavorites;
    private Boolean isUserRated;
    private long ratingCount;
    private long reviewCount;
    private double avgRatings;
    private RatingStatsDTO ratingStats;
    private List<String> imageUrls;
    private List<ReviewDTO> initialReviews;
}

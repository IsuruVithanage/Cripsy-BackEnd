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
    private Double avgRatings;
    private Long ratingCount;
    private Long reviewCount;
    private Boolean isUserRated;
    private Boolean isWatchlistAdded;
    private RatingStatsDTO ratingStats;
    private List<String> imageUrls;
    private List<ReviewDTO> initialReviews;

    @SuppressWarnings("unused")
    public double getAvgRatings(){
        return Math.round(this.avgRatings * 10.0) / 10.0;
    }
}

package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCardDTO {
    private Integer productId;
    private String name;
    private double price;
    private String description;
    private boolean isAddedToFavorites;
    private long ratingCount;
    private double avgRatings;
    private List<String> imageUrls;

    @SuppressWarnings("unused")
    public double getAvgRatings(){
        return Math.round(this.avgRatings * 10.0) / 10.0;
    }

    @SuppressWarnings("unused")
    public String getDescription(){
        Document document = Jsoup.parse(this.description);
        Element firstP = document.selectFirst("p");
        return firstP != null ? firstP.text() : "";
    }
}

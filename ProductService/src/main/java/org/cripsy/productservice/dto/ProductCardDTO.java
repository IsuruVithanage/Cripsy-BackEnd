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
    private long ratingCount;
    private double avgRatings;
    private String imageUrl;

    @SuppressWarnings("unused")
    public void setImageUrls(List<String> imageUrls) {
        if(imageUrls == null){
            this.imageUrl = null;
        } else {
            this.imageUrl = imageUrls.get(0);
        }
    }

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

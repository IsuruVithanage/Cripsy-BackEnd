package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Integer stock;
    private Long ratingCount;
    private Double avgRatings;
    private Integer quantity;
    private Double total;
    private Long reviewCount;
    private String imageUrl;

    @SuppressWarnings("unused")
    public String getDescription(){
        if(description == null || description.isEmpty()){
            return null;
        }

        Document document = Jsoup.parse(this.description);
        Element firstP = document.selectFirst("p");
        return firstP != null ? firstP.text() : "";
    }
}

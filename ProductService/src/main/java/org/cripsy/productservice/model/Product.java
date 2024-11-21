package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String name;
    private String description;
    private double price;
    private int stock;
    private double discount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageUrls> imageUrls;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ratings ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;



    public List<String> getImageUrls() {
    /*
      This method transforms the list of `ImageUrls` entities associated with this object
      into a list of their corresponding URL strings for easier access.
    */
        return imageUrls.stream()
                .map(ImageUrls::getUrl)
                .collect(Collectors.toList());
    }

    public void setImageUrls(List<String> imageUrls) {
    /*
        This method takes a list of URL strings and converts them into `ImageUrls` entities,
    */
        this.imageUrls = imageUrls.stream()
                .map(url -> new ImageUrls(this, url))
                .collect(Collectors.toList());
    }
}

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    private double price;
    private int stock;
    private double discount;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(updatable = false)
    private long ratingCount;

    @Column(updatable = false)
    private double avgRatings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageUrls> imageUrls;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ratings> ratings;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cart> cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @SuppressWarnings("unused")
    public List<String> getImageUrls() {
    /*
      This method transforms the list of `ImageUrls` entities associated with this object
      into a list of their corresponding URL strings for easier access.
    */
        if(this.imageUrls == null){
            return null;
        }
        return imageUrls
                .stream()
                .map(ImageUrls::getUrl)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    public void setImageUrls(List<String> imageUrls) {
    /*
        This method takes a list of URL strings and converts them into `ImageUrls` entities,
    */
        this.imageUrls = imageUrls
                .stream()
                .map(url -> new ImageUrls(this, url))
                .collect(Collectors.toList());
    }
}

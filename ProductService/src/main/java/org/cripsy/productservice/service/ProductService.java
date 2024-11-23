package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.model.Product;
import org.cripsy.productservice.model.Ratings;
import org.cripsy.productservice.repository.ProductRepository;
import org.cripsy.productservice.repository.RatingsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final RatingsRepository ratingsRepo;
    private final ModelMapper modelMapper;

    public List<ProductCardDTO> getAllProducts() {
        List<Product> productList = productRepo.findAll();
        return modelMapper.map(
            productList,
            new TypeToken<List<ProductCardDTO>>() {
            }.getType()
        );
    }

    public String addProduct(CreateProductDTO productDTO){
        // Add products
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return "Product saved";
    }

    public String updateProduct(UpdateProductDTO productDTO){
        Product product = modelMapper.map(productDTO, Product.class);
        productRepo.save(product);
        return "Product updated";
    }

    public String rateProduct(ReviewDTO reviewDTO){
        ratingsRepo.saveRating(reviewDTO);
        return "Rated";
    }

    public RatingStatsDTO getRatings(){
        return ratingsRepo.getRatingsSummary(1);
    }

    public String deleteProduct(Integer productId){
        productRepo.deleteById(productId);
        return "Product deleted";
    }

}

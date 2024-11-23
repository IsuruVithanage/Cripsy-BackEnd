package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.model.Product;
import org.cripsy.productservice.model.RatingId;
import org.cripsy.productservice.repository.ProductRepository;
import org.cripsy.productservice.repository.RatingsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


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


    public ProductItemDTO getProductById(Integer productId, String user) {
        Optional<Product> productOptional = productRepo.findById(productId);

        if(productOptional.isEmpty()){
            throw new RuntimeException("Product Not Found");
        }

        Product product = productOptional.get();
        ProductItemDTO productItem = modelMapper.map(product, ProductItemDTO.class);
        RatingStatsDTO ratingStats = ratingsRepo.getRatingsSummary(productId);
        List<ReviewDTO> reviews = ratingsRepo.findReviewsByProductId(productId, PageRequest.of(0, 2));
        boolean isUserRated = !user.isEmpty() && ratingsRepo.existsById(new RatingId(product, user));
        productItem.setRatingStats(ratingStats);
        productItem.setInitialReviews(reviews);
        productItem.setIsUserRated(isUserRated);
        return productItem;
    }


    public String addProduct(CreateProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return "Product saved";
    }


    public String updateProduct(UpdateProductDTO productDTO){
        Product product = modelMapper.map(productDTO, Product.class);
        productRepo.save(product);
        return "Product updated";
    }


    public String rateProduct(RateProductDTO rateProduct){
        ratingsRepo.saveRating(rateProduct);
        return "Rated";
    }


    public String deleteProduct(Integer productId){
        productRepo.deleteById(productId);
        return "Product deleted";
    }

}

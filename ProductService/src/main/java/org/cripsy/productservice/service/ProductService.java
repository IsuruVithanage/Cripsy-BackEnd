package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.model.Product;
import org.cripsy.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
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
        product.setReviews(new ArrayList<>());
        productRepo.save(product);
        return "Product updated";
    }

    public String rateProduct(ProductRatingDTO ratingDTO){
        int productId = ratingDTO.getProductId();
        int rating = ratingDTO.getRating();
//        int affectedRows = productRepo.updateProductRating(productId, rating);
//        return affectedRows == 0 ? "Product Not Found" : "Rated " + rating;
        return "Rated" + rating;
    }

    public String deleteProduct(Integer productId){
        productRepo.deleteById(productId);
        return "Product deleted";
    }

}

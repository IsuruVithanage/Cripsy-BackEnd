package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductDTO;
import org.cripsy.productservice.dto.ProductRatingDTO;
import org.cripsy.productservice.model.Product;
import org.cripsy.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepo.findAll();
        return modelMapper.map(
            productList,
            new TypeToken<List<ProductDTO>>() {
            }.getType()
        );
    }

    public String addProduct(ProductDTO productDTO){
        // Add products
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return "Product saved";
    }

    public String updateProduct(ProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return "Product updated";
    }

    public String rateProduct(ProductRatingDTO ratingDTO){
        int productId = ratingDTO.getProductId();
        int rating = ratingDTO.getRating();
        int affectedRows = productRepo.updateProductRating(productId, rating);
        return affectedRows == 0 ? "Product Not Found" : "Rated " + rating;
    }

    public String deleteProduct(Integer productId){
        productRepo.deleteById(productId);
        return "Product deleted";
    }

}
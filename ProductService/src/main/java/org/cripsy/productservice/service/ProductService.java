package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.model.Category;
import org.cripsy.productservice.model.Product;
import org.cripsy.productservice.repository.CategoryRepository;
import org.cripsy.productservice.repository.ProductRepository;
import org.cripsy.productservice.repository.RatingsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final RatingsRepository ratingsRepo;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public List<ProductCardDTO> getAllProducts() {
        List<Product> productList = productRepo.findAll();
        return modelMapper.map(
                productList,
                new TypeToken<List<ProductCardDTO>>() {
                }.getType()
        );
    }

    public List<UpdateProductDTO> getAllProductsDetails() {
        List<Product> productList = productRepo.findAll();
        List<UpdateProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList) {
            UpdateProductDTO dto = new UpdateProductDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            dto.setDiscount(product.getDiscount());
            dto.setCategory(product.getCategory().getCategoryId());

            List<String> imageUrls = productRepo.findImageUrls(dto.getProductId());

            dto.setImageUrls(imageUrls);

            productDTOList.add(dto);
        }

        return productDTOList;

    }


    public ProductItemDTO getProductById(Integer productId, Integer userId) {
        Optional<ProductItemDTO> productOptional = productRepo.findProductItemDetails(productId, userId);

        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product Not Found");
        }

        ProductItemDTO productItem = productOptional.get();
        List<String> imageUrls = productRepo.findImageUrls(productId);
        List<ReviewDTO> reviews = ratingsRepo.findReviewsByProductId(productId, PageRequest.of(0, 5));

        productItem.setInitialReviews(reviews);
        productItem.setImageUrls(imageUrls);
        return productItem;
    }


    public List<GetProductInfoDTO> getInfo(List<Integer> productIdList){
        return productRepo.getInfo(productIdList);
    }


    public List<ReviewDTO> getReviews(Integer productId, Integer pageNo) {
        return ratingsRepo.findReviewsByProductId(productId, PageRequest.of(pageNo - 1, 5));
    }

    public String addProduct(CreateProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);


        Category existingCategory = categoryRepository.findById(productDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Set the fetched Category in the Product object
        product.setCategory(existingCategory);

        // Save the Product
        productRepo.save(product);

        return "Product saved";
    }


    public String updateProduct(UpdateProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);

        Category existingCategory = categoryRepository.findById(productDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(existingCategory);
        productRepo.save(product);
        return "Product updated";
    }


    public ResponseEntity<List<ReviewDTO>> addReview(AddReviewDTO addReviewDTO) {
        int statusCode = 200;

        try {
            ratingsRepo.saveRating(addReviewDTO);
        } catch (DataIntegrityViolationException e){
            statusCode = 409; // User Already added a review
        } catch (Exception e){
            statusCode = 500;
        }

        List<ReviewDTO> reviews = this.getReviews(addReviewDTO.getProductId(), 1);
        return ResponseEntity.status(statusCode).body(reviews);
    }


    public String deleteProduct(Integer productId) {
        productRepo.deleteById(productId);
        return "Product deleted";
    }
}

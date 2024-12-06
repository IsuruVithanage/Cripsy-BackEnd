package org.cripsy.productservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    @Operation(summary = "Get All Products", description = "Fetch a list of all available products.", tags = "Product")
    public List<ProductCardDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getAllProductsDetails")
    @Operation(summary = "Get All Products", description = "Fetch a list of all available products.", tags = "Product")
    public List<UpdateProductDTO> getAllProductsDetails() {
        return productService.getAllProductsDetails();
    }

    @GetMapping("/{productId}/{userId}")
    @Operation(summary = "Get a Product", description = "Fetch the details of a single product.", tags = "Product")
    public ProductItemDTO getProductById(@PathVariable Integer productId, @PathVariable Integer userId){
        return productService.getProductById(productId, userId);
    }

    @GetMapping("/reviews/{productId}/{pageNo}")
    @Operation(summary = "Get Reviews", description = "Fetch a List of Reviews of a product with pagination", tags = "Reviews")
    public List<ReviewDTO> getReviews(@PathVariable Integer productId, @PathVariable Integer pageNo) {
        return productService.getReviews(productId, pageNo);
    }


    @PostMapping("/getInfo")
    @Operation(summary = "Get Product Info", description = "Fetch Information for a List of Products", tags = "Product")
    public List<GetProductInfoDTO> getInfo(@RequestBody List<Integer> productIdList){
        return productService.getInfo(productIdList);
    }


    @PostMapping("/review")
    @Operation(summary = "Add a Review", description = "Add a Rating to an existing product", tags = "Reviews")
    public ResponseEntity<List<ReviewDTO>> addReview(@Valid @RequestBody AddReviewDTO addReviewDTO){
        return productService.addReview(addReviewDTO);
    }


    @PostMapping("/add")
    @Operation(summary = "Add a new product", description = "Add a new product to the system.", tags = "Product")
    public String addProduct(@RequestBody CreateProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }


    @PutMapping("/update")
    @Operation(summary = "Update an product", description = "Update the details of an existing product.", tags = "Product")
    public String updateProduct(@Valid @RequestBody UpdateProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }


    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product", description = "Delete a product specified by its ID.", tags = "Product")
    public String deleteProduct(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }
}

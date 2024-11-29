package org.cripsy.productservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    @Operation(summary = "Get All Products", description = "Fetch a list of all available products.", tags = "User")
    public List<ProductCardDTO> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/{productId}/{userName}")
    @Operation(summary = "Get a Product", description = "Fetch the details of a single product.", tags = "User")
    public ProductItemDTO getProductById(@PathVariable Integer productId, @PathVariable String userName){
        return productService.getProductById(productId, userName);
    }


    @GetMapping("/{productId}/reviews/{pageNo}")
    @Operation(summary = "Get Reviews", description = "Fetch a set of Reviews of a product with pagination", tags = "User")
    public List<ReviewDTO> getReviews(@PathVariable Integer productId, @PathVariable Integer pageNo) {
        return productService.getReviews(productId, pageNo);
    }


    @PostMapping("/add")
    @Operation(summary = "Add a new product", description = "Add a new product to the system.", tags = "Admin")
    public String addProduct(@RequestBody CreateProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }


    @PutMapping("/update")
    @Operation(summary = "Update an product", description = "Update the details of an existing product.", tags = "Admin")
    public String updateProduct(@Valid @RequestBody UpdateProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }


    @PostMapping("/rate")
    @Operation(summary = "Rate a product", description = "Add a Rating to an existing product", tags = "User")
    public String rateProduct(@Valid @RequestBody RateProductDTO rateProductDTO){
        return productService.rateProduct(rateProductDTO);
    }


    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product", description = "Delete a product specified by its ID.", tags = "Admin")
    public String deleteProduct(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }
}

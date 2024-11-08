package org.cripsy.productservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductDTO;
import org.cripsy.productservice.dto.ProductRatingDTO;
import org.cripsy.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    @Operation(summary = "Get All Products", description = "Fetch a list of all available products.", tags = "User")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new product", description = "Add a new product to the system.", tags = "Admin")
    public String addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping("/update")
    @Operation(summary = "Update an product", description = "Update the details of an existing product.", tags = "Admin")
    public String updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    @PutMapping("/rate")
    @Operation(summary = "Rate a product", description = "Add a Rating to an existing product", tags = "User")
    public String rateProduct(@Valid @RequestBody ProductRatingDTO ratingDTO){
        return productService.rateProduct(ratingDTO);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product", description = "Delete a product specified by its ID.", tags = "Admin")
    public String deleteProduct(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }
}

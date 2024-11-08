package org.cripsy.productservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductDTO;
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
    @Operation(summary = "Get All Products", description = "Fetch a list of all available products.")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new product", description = "Add a new product to the system.")
    public String addProduct(@RequestBody ProductDTO product) {
        return productService.addProduct(product);
    }

    @PutMapping("/update")
    @Operation(summary = "Update an product", description = "Update the details of an existing product.")
    public String updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product specified by its ID.")
    public String deleteProduct(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }
}

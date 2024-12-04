package org.cripsy.exampleservice.controller;

import org.cripsy.exampleservice.dto.CustomerDTO;
import org.cripsy.exampleservice.dto.ProductDTO;
import org.cripsy.exampleservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAllProducts")
    @Operation(summary = "Get All Products", description = "Fetch a list of all available products.", tags = "User")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getAllProductCust")
    public List<CustomerDTO> getAllProductCust() {
        return productService.getAllProductCust();
    }

}

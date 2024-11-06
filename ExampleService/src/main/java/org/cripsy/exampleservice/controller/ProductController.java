package org.cripsy.exampleservice.controller;

import org.cripsy.exampleservice.dto.ProductDTO;
import org.cripsy.exampleservice.model.Product;
import org.cripsy.exampleservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAllProducts")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public List<ProductDTO> getProduct() {
        return productService.getAllProducts();
    }
}

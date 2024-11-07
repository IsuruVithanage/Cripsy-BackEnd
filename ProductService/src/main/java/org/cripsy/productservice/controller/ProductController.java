package org.cripsy.productservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductDTO;
import org.cripsy.productservice.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }
}

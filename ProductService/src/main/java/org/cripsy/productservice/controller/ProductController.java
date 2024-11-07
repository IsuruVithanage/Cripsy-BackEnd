package org.cripsy.productservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    @GetMapping("/getAll")
    public List<String> getAll() {
        String[] products = {"Product1", "Product2",};
        return List.of(products);
    }
}

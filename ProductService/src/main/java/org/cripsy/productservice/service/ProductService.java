package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductDTO;
import org.cripsy.productservice.model.Product;
import org.cripsy.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
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
                new TypeToken<List<ProductDTO>>() {}.getType()
        );
    }

}

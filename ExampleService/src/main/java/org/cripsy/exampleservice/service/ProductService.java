package org.cripsy.exampleservice.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> userList = productRepository.findAll();
        return modelMapper.map(userList, new TypeToken<List<ProductDTO>>() {
        }.getType());
    }
}

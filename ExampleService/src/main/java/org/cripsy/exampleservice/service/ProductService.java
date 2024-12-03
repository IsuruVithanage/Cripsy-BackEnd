package org.cripsy.exampleservice.service;

import org.cripsy.exampleservice.dto.CustomerDTO;
import org.cripsy.exampleservice.dto.ProductDTO;
import org.cripsy.exampleservice.model.Product;
import org.cripsy.exampleservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final WebClient webClient;

    @Autowired
    private ModelMapper modelMapper;

    public List<CustomerDTO> getAllProducts() {

        List<CustomerDTO> customers = getAllCustomers();

        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {}.getType());
    }


    public List<CustomerDTO> getAllCustomers() {
        return webClient.get()
                .uri("http://localhost:8081/api/customers")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CustomerDTO>>() {})
                .block();
    }

}

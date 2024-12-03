package org.cripsy.exampleservice.controller;

import org.cripsy.exampleservice.dto.ProductDTO;
import org.cripsy.exampleservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testGetAllProducts() throws Exception {
        // Mock data
        List<ProductDTO> mockProducts = Arrays.asList(
                new ProductDTO(1, "Product 1", "Description 1", 100.0),
                new ProductDTO(2, "Product 2", "Description 2", 200.0)
        );

        // Mock service
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Perform the request and assert the response
        mockMvc.perform(get("/api/products/getAllProducts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockProducts.size()))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[0].price").value(100.0));
    }
}

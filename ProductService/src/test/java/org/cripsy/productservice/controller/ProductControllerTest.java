package org.cripsy.productservice.controller;

import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    void testUpdateProduct() throws Exception {
        UpdateProductDTO updateProductDTO = new UpdateProductDTO();
        updateProductDTO.setProductId(1);
        updateProductDTO.setName("Test Product");

        Mockito.when(productService.updateProduct(any(UpdateProductDTO.class))).thenReturn("Product updated successfully");

        String requestBody = """
        {
            "productId": 1,
            "name": "Test Product"
        }
        """;

        mockMvc.perform(put("/api/product/update")
            .contentType("application/json")
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().string("Product updated successfully"));
    }

    @Test
    void testAddReview() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserName("User1");
        reviewDTO.setRating(5);
        reviewDTO.setComment("Great product!");
        reviewDTO.setRatedDate(null);

        Mockito.when(productService.addReview(any(AddReviewDTO.class))).thenReturn(ResponseEntity.ok(List.of(reviewDTO)));

        String requestBody = """
        {
            "productId": 1,
            "userId": 1,
            "rating": 5,
            "comment": "Great product!"
        }
        """;

        mockMvc.perform(post("/api/product/review")
            .contentType("application/json")
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userName").value("User1"))
            .andExpect(jsonPath("$[0].rating").value(5))
            .andExpect(jsonPath("$[0].comment").value("Great product!"));
    }

    @Test
    void testGetProductInfo() throws Exception {
        GetProductInfoDTO getProductInfoDTO = new GetProductInfoDTO(1, "Test Product", 4.5);
        Mockito.when(productService.getInfo(anyList())).thenReturn(List.of(getProductInfoDTO));

        mockMvc.perform(post("/api/product/getInfo/")
            .contentType("application/json")
            .content("[1]"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].productId").value(1))
            .andExpect(jsonPath("$[0].name").value("Test Product"))
            .andExpect(jsonPath("$[0].avgRatings").value(4.5));
    }

    @Test
    void testAddProduct() throws Exception {
        Mockito.when(productService.addProduct(any(CreateProductDTO.class))).thenReturn("Product added successfully");

        String requestBody = """
        {
            "name": "New Product",
            "price": 150.0
        }
        """;

        mockMvc.perform(post("/api/product/add")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully"));
    }

    @Test
    void testGetProductById() throws Exception {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setProductId(1);
        productItemDTO.setName("Test Product");
        productItemDTO.setPrice(100.0);

        Mockito.when(productService.getProductById(anyInt(), anyInt())).thenReturn(productItemDTO);

        mockMvc.perform(get("/api/product/{productId}/{userId}", 1, 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(1))
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void testGetReviews() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserName("User1");
        reviewDTO.setRating(5);
        reviewDTO.setComment("Great product!");

        Mockito.when(productService.getReviews(anyInt(), anyInt())).thenReturn(List.of(reviewDTO));

        mockMvc.perform(get("/api/product/reviews/{productId}/{pageNo}", 1, 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userName").value("User1"))
            .andExpect(jsonPath("$[0].rating").value(5))
            .andExpect(jsonPath("$[0].comment").value("Great product!"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.when(productService.deleteProduct(anyInt())).thenReturn("Product deleted successfully");

        mockMvc.perform(delete("/api/product/{productId}", 1))
            .andExpect(status().isOk())
            .andExpect(content().string("Product deleted successfully"));
    }
}


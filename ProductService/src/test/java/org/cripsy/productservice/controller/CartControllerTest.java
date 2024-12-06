package org.cripsy.productservice.controller;

import org.cripsy.productservice.dto.AddToCartDTO;
import org.cripsy.productservice.dto.CartItemDTO;
import org.cripsy.productservice.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    public void testUpdateCartQuantityWithValidReqBody() throws Exception {
        List<CartItemDTO> updatedCart = List.of(new CartItemDTO());
        Mockito.when(cartService.updateCartQuantity(any(AddToCartDTO.class))).thenReturn(updatedCart);

        String requestBody = """
            {
                "productId": 10,
                "userId": 100,
                "quantity": 50
            }
        """;

        mockMvc.perform(put("/api/product/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].productId").isEmpty())
            .andExpect(jsonPath("$[0].name").isEmpty());
    }


    @Test
    public void testUpdateCartQuantityWithInvalidReqBody() throws Exception {
        List<CartItemDTO> updatedCart = List.of(new CartItemDTO());
        Mockito.when(cartService.updateCartQuantity(any(AddToCartDTO.class))).thenReturn(updatedCart);

        String requestBody = """
            {
                "productId": "String",
                "userId": 100,
                "quantity": 50
            }
        """;

        mockMvc.perform(put("/api/product/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }



    @Test
    public void testAddToCartWithValidReqBody() throws Exception {
        Mockito.when(cartService.addToCart(any(AddToCartDTO.class))).thenReturn("Product added to cart");

        String requestBody = """
            {
                "productId": 1,
                "userId": 101,
                "quantity": 2
            }
        """;

        mockMvc.perform(post("/api/product/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().string("Product added to cart"));
    }


    @Test
    public void testAddToCartWithInvalidReqBody() throws Exception {
        Mockito.when(cartService.addToCart(any(AddToCartDTO.class))).thenReturn("Product added to cart");

        String requestBody = """
            {
                "productId": "string",
                "userId": 101,
                "quantity": 2
            }
        """;

        mockMvc.perform(post("/api/product/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCartItemsWithValidUserId() throws Exception {
        List<CartItemDTO> cartItems = List.of(new CartItemDTO());
        Mockito.when(cartService.getCartItems(any(Integer.class))).thenReturn(cartItems);

        mockMvc.perform(get("/api/product/cart/101")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].productId").isEmpty())
            .andExpect(jsonPath("$[0].name").isEmpty());
    }


    @Test
    public void testGetCartItemsWithInvalidUserId() throws Exception {
        List<CartItemDTO> cartItems = List.of(new CartItemDTO());
        Mockito.when(cartService.getCartItems(any(Integer.class))).thenReturn(cartItems);

        mockMvc.perform(get("/api/product/cart/string")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testRemoveFromCartWithValidArgs() throws Exception {
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setProductId(1);
        cartItem.setName("Test Product");

        List<CartItemDTO> updatedCart = List.of(cartItem);
        Mockito.when(cartService.removeFromCart(1, 101)).thenReturn(updatedCart);

        mockMvc.perform(delete("/api/product/cart/1/101")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].productId").value(1))
            .andExpect(jsonPath("$[0].name").value("Test Product"));
    }


    @Test
    public void testRemoveFromCartWithInvalidArgs() throws Exception {
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setProductId(1);
        cartItem.setName("Test Product");

        List<CartItemDTO> updatedCart = List.of(cartItem);
        Mockito.when(cartService.removeFromCart(1, 101)).thenReturn(updatedCart);

        mockMvc.perform(delete("/api/product/cart/string/101")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}


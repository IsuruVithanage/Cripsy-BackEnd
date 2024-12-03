package org.cripsy.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.*;
import org.cripsy.productservice.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart/{userId}")
    @Operation(summary = "Get Cart Items", description = "Fetch a List of Cart Items for a User", tags = "User")
    public List<CartItemDTO> getCartItems(@PathVariable Integer userId) {
        return cartService.getCartItems(userId);
    }


    @PostMapping("/cart")
    @Operation(summary = "Add to Cart", description = "Add a product to the user cart.", tags = "User")
    public String addToCart(@RequestBody AddToCartDTO addToCartDTO) {
        return cartService.addToCart(addToCartDTO);
    }


    @PutMapping("/cart")
    @Operation(summary = "Update Cart", description = "Update the quantity for a product in user cart.", tags = "User")
    public List<CartItemDTO> updateCartQuantity(@RequestBody AddToCartDTO addToCartDTO) {
        return cartService.updateCartQuantity(addToCartDTO);
    }


    @DeleteMapping("/cart/{productId}/{userId}")
    @Operation(summary = "Remove Cart Item", description = "Delete a Cart item specified by its ProductId and UserID.", tags = "User")
    public List<CartItemDTO> removeFromCart(@PathVariable Integer productId, @PathVariable Integer userId){
        return cartService.removeFromCart(productId, userId);
    }
}

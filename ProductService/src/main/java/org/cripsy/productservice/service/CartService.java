package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.AddToCartDTO;
import org.cripsy.productservice.dto.CartItemDTO;
import org.cripsy.productservice.repository.CartRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepo;

    public List<CartItemDTO> getCartItems(Integer userId){
        return cartRepo.findByUserId(userId);
    }


    public String addToCart(AddToCartDTO addToCartDTO){
        cartRepo.upsertCart(addToCartDTO);
        return "Added to cart";
    }


    public List<CartItemDTO> updateCartQuantity(AddToCartDTO addToCartDTO){
        cartRepo.upsertCart(addToCartDTO);
        return this.getCartItems(addToCartDTO.getUserId());
    }


    public List<CartItemDTO> removeFromCart(Integer productId, Integer userId){
        cartRepo.removeFromCart(productId, userId);
        return this.getCartItems(userId);
    }

}

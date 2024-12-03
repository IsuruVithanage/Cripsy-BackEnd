package org.cripsy.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.service.FavouritesService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class FavouritesController {
    private final FavouritesService favouritesService;

    @GetMapping("/favourites/{userId}")
    @Operation(summary = "Get Favourite Items", description = "Fetch a List of favourite Items for a User", tags = "User")
    public List<ProductCardDTO> getCartItems(@PathVariable Integer userId) {
        return favouritesService.getFavouriteProducts(userId);
    }


    @PostMapping("/favourite/{productId}/{userId}")
    @Operation(summary = "Add Item to Favourites", description = "Add a new product to the user favourites.", tags = "User")
    public String addToFavourites(@PathVariable Integer productId, @PathVariable Integer userId) {
        return favouritesService.addToFavourites(productId, userId);
    }


    @DeleteMapping("/favourite/{productId}/{userId}/{isResponseExpected}")
    @Operation(summary = "Remove Item from Favourites", description = "Remove a product from user favourites", tags = "User")
    public List<ProductCardDTO> removeFavourite(
            @PathVariable Integer productId,
            @PathVariable Integer userId,
            @PathVariable Boolean isResponseExpected
    ){
        return favouritesService.removeFavourite(productId, userId, isResponseExpected);
    }

}

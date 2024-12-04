package org.cripsy.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.service.WatchlistServiceService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class WatchlistController {
    private final WatchlistServiceService watchlistService;

    @GetMapping("/watchlist/{userId}")
    @Operation(summary = "Get Watchlist Items", description = "Fetch a List of watchlist Items for a User", tags = "Watchlist")
    public List<ProductCardDTO> getWatchlistItems(@PathVariable Integer userId) {
        return watchlistService.getWatchlistItems(userId);
    }


    @PostMapping("/watchlist/{productId}/{userId}")
    @Operation(summary = "Add Item to Watchlist", description = "Add a new product to the user watchlist.", tags = "Watchlist")
    public String addToWatchlist(@PathVariable Integer productId, @PathVariable Integer userId) {
        return watchlistService.addToWatchlist(productId, userId);
    }


    @DeleteMapping("/watchlist/{productId}/{userId}/{isResponseExpected}")
    @Operation(summary = "Remove Item from Watchlist", description = "Remove a product from user watchlist.", tags = "Watchlist")
    public List<ProductCardDTO> removeFromWatchlist(
        @PathVariable Integer productId,
        @PathVariable Integer userId,
        @PathVariable Boolean isResponseExpected
    ){
        return watchlistService.removeFromWatchlist(productId, userId, isResponseExpected);
    }
}

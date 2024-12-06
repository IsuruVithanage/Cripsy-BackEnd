package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.repository.WatchlistRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class WatchlistServiceService {
    private final WatchlistRepository watchlistRepo;


    public List<ProductCardDTO> getWatchlistItems(Integer userId) {
         return watchlistRepo.findAllByUserId(userId);
    }


    public String addToWatchlist(Integer productId, Integer userId){
        watchlistRepo.addToWatchlist(productId, userId);
        return "Added To Watchlist";
    }


    public List<ProductCardDTO> removeFromWatchlist(Integer productId, Integer userId, Boolean isResponseExpected){
        watchlistRepo.removeFromWatchlist(productId, userId);
        if(!isResponseExpected){
            return null;
        } else {
            return this.getWatchlistItems(userId);
        }
    }
}

package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.repository.FavouritesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class FavouritesService {
    private final FavouritesRepository favouritesRepo;


    public List<ProductCardDTO> getFavouriteProducts(Integer userId) {
         return favouritesRepo.findAllByUserId(userId);
    }


    public String addToFavourites(Integer productId, Integer userId){
        favouritesRepo.addToFavourites(productId, userId);
        return "Added To Favourites";
    }


    public List<ProductCardDTO> removeFavourite(Integer productId, Integer userId, Boolean isResponseExpected){
        favouritesRepo.removeFavourite(productId, userId);
        if(!isResponseExpected){
            return null;
        } else {
            return this.getFavouriteProducts(userId);
        }
    }
}

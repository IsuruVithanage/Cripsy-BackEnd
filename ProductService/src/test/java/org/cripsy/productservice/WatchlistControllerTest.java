package org.cripsy.productservice;

import org.cripsy.productservice.dto.ProductCardDTO;
import org.cripsy.productservice.service.WatchlistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WatchlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WatchlistService watchlistService;

    @Test
    public void testAddToWatchlist() throws Exception {
        int productId = 1;
        int userId = 100;

        Mockito.when(watchlistService.addToWatchlist(any(Integer.class), any(Integer.class)))
            .thenReturn("Added To Watchlist");

        mockMvc.perform(post("/api/product/watchlist/{productId}/{userId}", productId, userId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Added To Watchlist"));
    }

    @Test
    public void testGetWatchlistItems() throws Exception {
        int userId = 100;

        Mockito.when(watchlistService.getWatchlistItems(any(Integer.class)))
            .thenReturn(Arrays.asList(new ProductCardDTO(), new ProductCardDTO()));

        mockMvc.perform(get("/api/product/watchlist/{userId}", userId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testRemoveFromWatchlist() throws Exception {
        int productId = 1;
        int userId = 100;
        boolean isResponseExpected = true;

        Mockito.when(watchlistService.removeFromWatchlist(any(Integer.class), any(Integer.class), any(Boolean.class)))
            .thenReturn(Arrays.asList(new ProductCardDTO(), new ProductCardDTO()));

        mockMvc.perform(delete("/api/product/watchlist/{productId}/{userId}/{isResponseExpected}", productId, userId, isResponseExpected)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }
}


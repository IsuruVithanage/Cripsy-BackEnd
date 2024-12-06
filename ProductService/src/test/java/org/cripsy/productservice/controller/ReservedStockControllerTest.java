package org.cripsy.productservice.controller;

import org.cripsy.productservice.dto.ReservedStockDTO;
import org.cripsy.productservice.service.ReservedStockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReservedStockController.class)
public class ReservedStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservedStockService reservedStockService;

    @Test
    public void testInitializeReservation() throws Exception {
        Mockito.when(reservedStockService.initializeReservation(any()))
            .thenReturn(12345);

        String requestBody = """
            [
                {"productId": 1, "quantity": 10},
                {"productId": 2, "quantity": 5}
            ]
        """;

        mockMvc.perform(post("/api/product/reserve/initiate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().string("12345"));
    }

    @Test
    public void testConfirmReservation() throws Exception {
        int transactionId = 12345;

        Mockito.when(reservedStockService.confirmReservation(transactionId))
            .thenReturn(Arrays.asList(new ReservedStockDTO(), new ReservedStockDTO()));

        mockMvc.perform(post("/api/product/reserve/confirm/{transactionId}", transactionId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testCancelReservation() throws Exception {
        int transactionId = 12345;

        Mockito.when(reservedStockService.cancelReservation(transactionId))
            .thenReturn("Reservation Cancelled");

        mockMvc.perform(post("/api/product/reserve/cancel/{transactionId}", transactionId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Reservation Cancelled"));
    }
}
package org.cripsy.orderservice.controller;

import org.cripsy.orderservice.repository.PaymentRepository;
import org.cripsy.orderservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private PaymentRepository paymentRepository;


    @Test
    void testStartPayment() throws Exception {
        String requestBody = """
        {
            "order_id": "12345",
            "items": "Sample Item",
            "amount": "100.00",
            "currency": "LKR",
            "first_name": "John",
            "last_name": "Doe",
            "email": "john.doe@example.com",
            "phone": "0771234567",
            "address": "123 Main Street",
            "city": "Colombo",
            "country": "Sri Lanka"
        }
        """;

        Mockito.when(paymentService.generateHash(anyString())).thenReturn("new hash");

        mockMvc.perform(post("/payment/start")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("merchant_id").isNotEmpty())
            .andExpect(jsonPath("hash").value("NEW HASH"));
    }
}

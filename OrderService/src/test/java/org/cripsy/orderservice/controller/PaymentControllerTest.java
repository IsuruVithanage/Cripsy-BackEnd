package org.cripsy.orderservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;


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

        mockMvc.perform(post("/payment/start")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }
}

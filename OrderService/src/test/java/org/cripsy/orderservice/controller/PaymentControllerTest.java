//package org.cripsy.orderservice.controller;
//
//public class PaymentControllerTest {
//}
//
//package com.example.orderservice.controller;
//
//        import com.example.orderservice.dto.PaymentRequest;
//        import com.example.orderservice.dto.PaymentNotification;
//        import com.fasterxml.jackson.databind.ObjectMapper;
//        import org.junit.jupiter.api.BeforeEach;
//        import org.junit.jupiter.api.Test;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//        import org.springframework.http.MediaType;
//        import org.springframework.test.web.servlet.MockMvc;
//
//        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(PaymentController.class)
//public class PaymentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private PaymentRequest paymentRequest;
//    private PaymentNotification paymentNotification;
//
//    @BeforeEach
//    void setup() {
//        paymentRequest = new PaymentRequest();
//        paymentRequest.setOrder_id("123");
//        paymentRequest.setAmount("100.00");
//        paymentRequest.setCurrency("USD");
//
//        paymentNotification = new PaymentNotification();
//        paymentNotification.setOrder_id("123");
//        paymentNotification.setPayhere_amount("100.00");
//        paymentNotification.setPayhere_currency("USD");
//        paymentNotification.setStatus_code("SUCCESS");
//        paymentNotification.setMd5sig("signature");
//    }
//
//    @Test
//    void testStartPayment() throws Exception {
//        mockMvc.perform(post("/payment/start")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(paymentRequest)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testPaymentNotification() throws Exception {
//        mockMvc.perform(post("/payment/notify")
//                        .param("notification", objectMapper.writeValueAsString(paymentNotification)))
//                .andExpect(status().isOk());
//    }
//}

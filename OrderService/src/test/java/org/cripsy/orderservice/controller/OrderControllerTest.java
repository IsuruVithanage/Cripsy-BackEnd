package org.cripsy.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    void testCreateOrder() throws Exception {
        int customerID = 1;

        String requestBody = """
        {
            "customerID": %d,
            "items": []
        }
        """.formatted(customerID);

        OrderDTO order = new OrderDTO();
        order.setCustomerID(customerID);
        order.setItems(null);

        Mockito.when(orderService.createOrder(any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders/createOrder")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

//    @Test
//    void testGetOrderById() throws Exception {
//        mockMvc.perform(get("/api/orders/{id}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.orderId").value(1));
//    }
//
//    @Test
//    void testUpdateOrder() throws Exception {
//        mockMvc.perform(put("/api/orders/updateOrder/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderDTO)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testGetAllOrders() throws Exception {
//        mockMvc.perform(get("/api/orders/getAllOrders"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testDeleteOrder() throws Exception {
//        mockMvc.perform(delete("/api/orders/deleteOrder/{id}", 1))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testGetOrdersByStatus() throws Exception {
//        mockMvc.perform(get("/api/orders/status/{status}", "PENDING"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testGetTotalSumOfTotalPrice() throws Exception {
//        mockMvc.perform(get("/api/orders/getSumTotal"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testGetMonthlyTotalSumOfTotalPrice() throws Exception {
//        mockMvc.perform(get("/api/orders/getMonthlySumTotal"))
//                .andExpect(status().isOk());
//    }
}

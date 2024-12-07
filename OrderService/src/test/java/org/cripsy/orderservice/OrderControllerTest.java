package org.cripsy.orderservice;

import org.cripsy.orderservice.dto.OrderDTO;
import org.cripsy.orderservice.dto.OrderDetailDTO;
import org.cripsy.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    void testCreateOrder() throws Exception {
        String requestBody = """
        {
            "customerID": 1,
            "items": []
        }
        """;

        Mockito.when(orderService.createOrder(any(OrderDTO.class))).thenReturn(new OrderDTO());

        mockMvc.perform(post("/api/orders/createOrder")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void testGetOrderById() throws Exception {
        Mockito.when(orderService.getOrderById(anyInt())).thenReturn(new OrderDetailDTO());

        mockMvc.perform(get("/api/orders/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateOrder() throws Exception {
        String requestBody = """
        {
            "customerID": 1,
            "items": []
        }
        """;

        Mockito.when(orderService.updateOrder(anyInt(), any(OrderDTO.class))).thenReturn(new OrderDTO());

        mockMvc.perform(put("/api/orders/updateOrder/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void testGetAllOrders() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(new OrderDetailDTO()));

        mockMvc.perform(get("/api/orders/getAllOrders")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteOrder() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrder(anyInt());

        mockMvc.perform(delete("/api/orders/deleteOrder/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetOrdersByStatus() throws Exception {
        Mockito.when(orderService.getOrdersByStatus(anyString())).thenReturn(List.of(new OrderDetailDTO()));

        mockMvc.perform(get("/api/orders/status/{status}", "PENDING")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetAllByCustomerId() throws Exception {
        Mockito.when(orderService.getAllByCustomerId(anyInt())).thenReturn(List.of(new OrderDetailDTO()));

        mockMvc.perform(get("/api/orders/getAllByCustomer/{customerID}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetTotalSumOfTotalPrice() throws Exception {
        Mockito.when(orderService.getTotalSumOfTotalPrice()).thenReturn(1000.0);

        mockMvc.perform(get("/api/orders/getSumTotal")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("1000.0"));
    }

    @Test
    void testGetMonthlyTotalSumOfTotalPrice() throws Exception {
        Mockito.when(orderService.getMonthlyTotalSumOfTotalPrice()).thenReturn(List.of(new HashMap<>()));

        mockMvc.perform(get("/api/orders/getMonthlySumTotal")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}

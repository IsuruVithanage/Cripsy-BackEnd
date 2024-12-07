package org.cripsy.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cripsy.customerservice.dto.AuthDTO;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.cripsy.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void testGetCustomerById() throws Exception {
        Mockito.when(customerService.getCustomerById(anyLong())).thenReturn(new CustomerDTO());

        mockMvc.perform(get("/api/customers/{id}", 1))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        String requestBody = """
        {
              "id": 0,
              "userName": "string",
              "email": "string",
              "password": "string",
              "address": "string",
              "contact": "string",
              "district": "string"
        }
        """;

        Mockito.when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(new CustomerDTO());

        mockMvc.perform(put("/api/customers/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Mockito.when(customerService.deleteCustomer(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/customers/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        String requestBody = """
        {
            "username": "string",
            "email": "string",
            "password": "string"
        }
        """;

        Mockito.doNothing().when(customerService).saveCustomer(any(AuthDTO.class));

        mockMvc.perform(post("/api/customers/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void testLoginCustomer() throws Exception {
        String requestBody = """
        {
            "username": "string",
            "email": "string",
            "password": "string"
        }
        """;

        Mockito.when(customerService.findCustomerByUsername(anyString())).thenReturn(new CustomerDTO());

        mockMvc.perform(post("/api/customers/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(new CustomerDTO()));

        mockMvc.perform(get("/api/customers")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetTotalCustomers() throws Exception {
        Mockito.when(customerService.getTotalCustomers()).thenReturn(100L);

        mockMvc.perform(get("/api/customers/total")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("100"));
    }
}


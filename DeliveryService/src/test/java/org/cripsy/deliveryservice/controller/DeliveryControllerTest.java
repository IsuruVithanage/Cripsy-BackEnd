package org.cripsy.deliveryservice.controller;

import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.service.DeliveryService;
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


@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService deliveryService;

    @Test
    public void testGetDeliveryPersonById() throws Exception {
        Mockito.when(deliveryService.getDeliveryPersonById(anyInt())).thenReturn(new DeliveryDTO());

        mockMvc.perform(get("/api/delivery/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testCreateDeliveryPerson() throws Exception {
        Mockito.when(deliveryService.createDeliveryPerson(any(DeliveryDTO.class))).thenReturn(new DeliveryDTO());

        String requestBody = """
        {
          "personId": 2,
          "name": "John Doe",
          "email": "johndoe@example.com",
          "password": "password123",
          "contact": "1234567890",
          "availability": true
        }
        """;

        mockMvc.perform(post("/api/delivery")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    public void testUpdateDeliveryPerson() throws Exception {
        Mockito.when(deliveryService.updateDeliveryPerson(anyInt(), any(DeliveryDTO.class))).thenReturn(new DeliveryDTO());

        String requestBody = """
        {
          "personId": 1,
          "name": "Jane Doe",
          "email": "janedoe@example.com",
          "password": "newpassword123",
          "contact": "9876543210",
          "availability": false
        }
        """;

        mockMvc.perform(put("/api/delivery/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDeliveryPerson() throws Exception {
        Mockito.when(deliveryService.deleteDeliveryPerson(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/api/delivery/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    public void testFindDeliveryByUsername() throws Exception {
        Mockito.when(deliveryService.findDeliveryByUsername(anyString())).thenReturn(new DeliveryDTO());

        String requestBody = """
        {
          "username": "johndoe",
          "email": "johndoe@example.com",
          "password": "password123"
        }
        """;

        mockMvc.perform(post("/api/delivery/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllDeliveryPersons() throws Exception {
        Mockito.when(deliveryService.getAllDeliveryPersons()).thenReturn(List.of(new DeliveryDTO()));

        mockMvc.perform(get("/api/delivery/getAll")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}


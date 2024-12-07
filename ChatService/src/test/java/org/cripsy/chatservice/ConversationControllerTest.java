package org.cripsy.chatservice;

import org.cripsy.chatservice.dto.ConversationDTO;
import org.cripsy.chatservice.service.ConversationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversationService conversationService;

    @Test
    public void testCreateConversation() throws Exception {
        String requestBody = """
            {
              "conversationId": 0,
              "adminId": 0,
              "customerId": 0,
              "customerName": "string"
            }
        """;

        Mockito.when(conversationService.createConversation(any(ConversationDTO.class))).thenReturn(new ConversationDTO());

        mockMvc.perform(post("/api/conversations/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllConversations() throws Exception {
        Mockito.when(conversationService.getAllConversations()).thenReturn(List.of(new ConversationDTO()));

        mockMvc.perform(get("/api/conversations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetConversationById() throws Exception {
        Mockito.when(conversationService.getConversationById(anyInt())).thenReturn(new ConversationDTO());

        mockMvc.perform(get("/api/conversations/{conversationId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testFindConversationByCustomerId() throws Exception {
        Mockito.when(conversationService.findMessagesByCustomerId(anyInt())).thenReturn(new ConversationDTO());

        mockMvc.perform(get("/api/conversations/getConversation/{customerId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteConversation() throws Exception {
        Mockito.doNothing().when(conversationService).deleteConversation(anyInt());

        mockMvc.perform(delete("/api/conversations/delete/{conversationId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}


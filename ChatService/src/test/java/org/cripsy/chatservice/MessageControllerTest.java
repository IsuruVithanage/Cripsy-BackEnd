package org.cripsy.chatservice;

import org.cripsy.chatservice.dto.MessageDTO;
import org.cripsy.chatservice.service.MessageService;
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
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testCreateMessage() throws Exception {
        String requestBody = """
            {
               "messageId": 0,
               "conversationId": 0,
               "sender": "string",
               "message": "string",
               "dateTime": "2024-12-07T09:50:31.047Z"
            }
        """;

        Mockito.when(messageService.createMessage(any(MessageDTO.class))).thenReturn(new MessageDTO());

        mockMvc.perform(post("/api/messages/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetMessageById() throws Exception {
        Mockito.when(messageService.getMessageById(anyInt())).thenReturn(new MessageDTO());

        mockMvc.perform(get("/api/messages/{messageId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllMessages() throws Exception {
        Mockito.when(messageService.getMessagesByConversationId(anyInt())).thenReturn(List.of(new MessageDTO()));

        mockMvc.perform(get("/api/messages/getAllMessages/{conversationId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}


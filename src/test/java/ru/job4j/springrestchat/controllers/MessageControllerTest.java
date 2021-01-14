package ru.job4j.springrestchat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.springrestchat.SpringRestChatApplication;
import ru.job4j.springrestchat.models.Message;
import ru.job4j.springrestchat.models.Person;
import ru.job4j.springrestchat.models.Role;
import ru.job4j.springrestchat.models.Room;
import ru.job4j.springrestchat.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringRestChatApplication.class)
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageRepository repository;

    private Message testMessage;
    private Message testSavedMessage;
    private final String API = "/messages/";
    private final String API_ID = "/messages/{id}";

    @BeforeEach
    private void generateTestMessages() {
        Role role = Role.of("role1");
        role.setId(1);

        Person person = Person.of(
                "testPerson",
                "123",
                role);
        person.setId(1);

        Room room = Room.of("room");
        room.setId(1);

        Message preparingMessage = Message.of("text1",
                person,
                room);
        preparingMessage.setId(1);
        testSavedMessage = preparingMessage;

        testMessage = Message.of("text1",
                person,
                room);
    }

    @Test
    @WithMockUser
    public void findAllMessages() throws Exception {
        List<Message> messages = List.of(
                testSavedMessage
        );

        when(repository.findAll()).thenReturn(messages);

        MvcResult result = mockMvc.perform(get(API))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(messages);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void findMessageById() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(testSavedMessage));

        MvcResult result = mockMvc.perform(get(API_ID, 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedMessage);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void createMessage() throws Exception {
        when(repository.save(testMessage)).thenReturn(testSavedMessage);

        MvcResult result = mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMessage)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedMessage);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void updateMessage() throws Exception {
        when(repository.save(testSavedMessage)).thenReturn(testSavedMessage);
        when(repository.existsById(1)).thenReturn(true);

        mockMvc.perform(put(API_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSavedMessage)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteMessage() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete(API_ID, 1))
                .andExpect(status().isOk());
    }
}

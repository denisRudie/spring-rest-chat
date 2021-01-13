package ru.job4j.springrestchat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.springrestchat.models.Room;
import ru.job4j.springrestchat.repositories.RoomRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomRepository repository;

    private Room testRoom;
    private Room testSavedRoom;
    private final String API = "/rooms/";
    private final String API_ID = "/rooms/{id}";

    @BeforeEach
    private void generateTestRooms() {
        Room preparingRoom = Room.of("room1");
        preparingRoom.setId(1);
        testSavedRoom = preparingRoom;

        testRoom = Room.of("room1");
    }

    @Test
    public void findAllRooms() throws Exception {
        List<Room> rooms = List.of(
                testSavedRoom
        );

        when(repository.findAll()).thenReturn(rooms);

        MvcResult result = mockMvc.perform(get(API))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(rooms);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void findRoomById() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(testSavedRoom));

        MvcResult result = mockMvc.perform(get(API_ID, 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedRoom);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void createRoom() throws Exception {
        when(repository.save(testRoom)).thenReturn(testSavedRoom);

        MvcResult result = mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRoom)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedRoom);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void updateRoom() throws Exception {
        when(repository.save(testSavedRoom)).thenReturn(testSavedRoom);
        when(repository.existsById(1)).thenReturn(true);

        mockMvc.perform(put(API_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSavedRoom)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRoom() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete(API_ID, 1))
                .andExpect(status().isOk());
    }

}

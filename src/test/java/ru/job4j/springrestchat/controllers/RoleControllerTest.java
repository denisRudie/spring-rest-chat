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
import ru.job4j.springrestchat.models.Role;
import ru.job4j.springrestchat.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleRepository repository;

    private Role testRole;
    private Role testSavedRole;
    private final String API = "/roles/";
    private final String API_ID = "/roles/{id}";

    @BeforeEach
    private void generateTestRoles() {
        Role preparingRole = Role.of("role1");
        preparingRole.setId(1);
        testSavedRole = preparingRole;

        testRole = Role.of("role1");
    }

    @Test
    public void findAllRoles() throws Exception {
        List<Role> roles = List.of(
                testSavedRole
        );

        when(repository.findAll()).thenReturn(roles);

        MvcResult result = mockMvc.perform(get(API))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(roles);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void findRoleById() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(testSavedRole));

        MvcResult result = mockMvc.perform(get(API_ID, 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedRole);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void createRole() throws Exception {
        when(repository.save(testRole)).thenReturn(testSavedRole);

        MvcResult result = mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRole)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedRole);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void updateRole() throws Exception {
        when(repository.save(testSavedRole)).thenReturn(testSavedRole);
        when(repository.existsById(1)).thenReturn(true);

        mockMvc.perform(put(API_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSavedRole)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRole() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete(API_ID, 1))
                .andExpect(status().isOk());
    }
}

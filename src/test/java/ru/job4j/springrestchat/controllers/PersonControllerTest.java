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
import ru.job4j.springrestchat.models.Person;
import ru.job4j.springrestchat.models.Role;
import ru.job4j.springrestchat.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonRepository repository;

    private Person testPerson;
    private Person testSavedPerson;
    private final String API = "/people/";
    private final String API_ID = "/people/{id}";

    @BeforeEach
    private void generateTestPeople() {
        Role role = Role.of("role1");
        role.setId(1);

        Person preparingPerson = Person.of(
                "user1",
                "pwd1",
                role);
        preparingPerson.setId(1);
        testSavedPerson = preparingPerson;

        testPerson = Person.of(
                "user1",
                "pwd1",
                role);
    }

    @Test
    public void findAllPeople() throws Exception {
        List<Person> people = List.of(
                testSavedPerson
        );

        when(repository.findAll()).thenReturn(people);

        MvcResult result = mockMvc.perform(get(API))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(people);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void findPersonById() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(testSavedPerson));

        MvcResult result = mockMvc.perform(get(API_ID, 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedPerson);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void createPerson() throws Exception {
        when(repository.save(testPerson)).thenReturn(testSavedPerson);

        MvcResult result = mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPerson)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedPerson);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void updatePerson() throws Exception {
        when(repository.save(testSavedPerson)).thenReturn(testSavedPerson);
        when(repository.existsById(1)).thenReturn(true);

        mockMvc.perform(put(API_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSavedPerson)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePerson() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete(API_ID, 1))
                .andExpect(status().isOk());
    }
}

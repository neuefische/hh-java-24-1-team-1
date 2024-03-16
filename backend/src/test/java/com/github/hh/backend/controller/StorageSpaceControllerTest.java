package com.github.hh.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hh.backend.model.ErrorMessage;
import com.github.hh.backend.model.StorageSpace;
import com.github.hh.backend.model.StorageSpaceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StorageSpaceControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllStorageSpaces_whenEmpty_thenEmpty() throws Exception {
        // Given
        List<StorageSpace> expected = List.of();

        // When
        // Then
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/storage"))
                .andExpect(status().isOk())
                .andReturn();
        List<StorageSpace> actual = objectMapper.readValue(resultJson.getResponse().getContentAsString(), new TypeReference<>() {});

        assertEquals(expected, actual);
    }

    @Test
    void getAllStorageSpaces_whenSomething_thenSomething() throws Exception {
        // Given
        String storageSpaceName1 = "storageSpace1";
        mvc.perform(MockMvcRequestBuilders.post("/api/storage").content(storageSpaceName1)).andExpect(status().isCreated());

        String storageSpaceName2 = "storageSpace2";
        mvc.perform(MockMvcRequestBuilders.post("/api/storage").content(storageSpaceName2)).andExpect(status().isCreated());


        // When
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/storage"))
                .andExpect(status().isOk())
                .andReturn();

        List<StorageSpace> actual = objectMapper.readValue(resultJson.getResponse().getContentAsString(), new TypeReference<>() {});

        // Then
        assertEquals(2, actual.size());
        assertEquals(storageSpaceName1, actual.getFirst().storageSpaceName());
        assertEquals(storageSpaceName2, actual.getLast().storageSpaceName());
        assertFalse(actual.getFirst().isOccupied());
        assertFalse(actual.getLast().isOccupied());
        assertNotNull(actual.getFirst().id());
        assertNotNull(actual.getLast().id());
    }

    @Test
    void getStorageSpaceById_whenThereIs_thenReturn() throws Exception {
        // Given
        String storageSpaceName = "storageSpace";
        MvcResult expectedJson = mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceName)).andReturn();
        StorageSpace expected = objectMapper.readValue(expectedJson.getResponse().getContentAsString(), StorageSpace.class);

        // When
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/storage/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.id())
                )
                .andExpect(status().isOk())
                .andReturn();

        StorageSpace actual = objectMapper.readValue(resultJson.getResponse().getContentAsString(), StorageSpace.class);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getStorageSpaceById_whenThereIsNot_thenThrow() throws Exception {
        // Given
        String id = "1";

        // When
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/storage/" +id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(id)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorMessage errorMessage = objectMapper.readValue(resultJson.getResponse().getContentAsString(), ErrorMessage.class);

        assertEquals("Storage space with ID 1 does not exist", errorMessage.errorMsg());
    }

    @Test
    void addNewStorageSpace() throws Exception {
        // Given
        String storageSpaceName = "storageSpace";

        // When
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceName)).andReturn();
        StorageSpaceDTO result = objectMapper.readValue(resultJson.getResponse().getContentAsString(), StorageSpaceDTO.class);

        // Then
        assertEquals(storageSpaceName, result.storageSpaceName());
        assertFalse(result.isOccupied());
    }

    @Test
    void toggleStorageSpaceOccupation_whenOccupied_thenUnoccupied() throws Exception {
        // Given
        String storageSpaceName = "storageSpace";
        MvcResult expectedJson = mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceName)).andReturn();
        StorageSpace expected = objectMapper.readValue(expectedJson.getResponse().getContentAsString(), StorageSpace.class);

        // When
        mvc.perform(MockMvcRequestBuilders.put("/api/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.id())
                )
                .andExpect(status().isOk())
                .andReturn();

        // Then
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/storage/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.id())
                )
                .andExpect(status().isOk())
                .andReturn();

        StorageSpace actual = objectMapper.readValue(resultJson.getResponse().getContentAsString(), StorageSpace.class);

        assertEquals(expected.withOccupied(true), actual);
    }

    @Test
    void toggleStorageSpaceOccupation_whenUnoccupied_thenOccupied() throws Exception {
        // Given
        String storageSpaceName = "storageSpace";
        MvcResult expectedJson = mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceName)).andReturn();
        StorageSpace expected = objectMapper.readValue(expectedJson.getResponse().getContentAsString(), StorageSpace.class);

        // When
        mvc.perform(MockMvcRequestBuilders.put("/api/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.id())
                )
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.put("/api/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.id())
                )
                .andExpect(status().isOk())
                .andReturn();

        // Then
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/storage/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.id())
                )
                .andExpect(status().isOk())
                .andReturn();

        StorageSpace actual = objectMapper.readValue(resultJson.getResponse().getContentAsString(), StorageSpace.class);

        assertEquals(expected.withOccupied(false), actual);
    }

    @Test
    void toggleStorageSpaceOccupation_whenDoesNotExist_thenThrow() throws Exception {
        // Given
        String id = "1";

        // When
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.put("/api/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(id)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorMessage errorMessage = objectMapper.readValue(resultJson.getResponse().getContentAsString(), ErrorMessage.class);

        assertEquals("Storage space with ID 1 does not exist", errorMessage.errorMsg());
    }
}
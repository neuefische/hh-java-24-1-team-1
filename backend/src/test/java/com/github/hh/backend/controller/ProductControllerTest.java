package com.github.hh.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hh.backend.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);
        expectedProduct = expectedProduct.withName("Updated Product").withAmount(5).withDescription("Updated Description");
        String productJson = objectMapper.writeValueAsString(expectedProduct);

        // When and Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Product updatedProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);

        assertEquals(expectedProduct.withName("Updated Product").withAmount(5).withDescription("Updated Description"), updatedProduct);
    }

    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        // When and Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Product saveProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        assertEquals(productDTO.name(), saveProduct.name());
        assertEquals(productDTO.amount(), saveProduct.amount());
        assertEquals(productDTO.description(), saveProduct.description());
        assertNotNull(saveProduct.id());
    }

    @Test
    void getProductById_returnTestProduct_whenCalledByCorrectId() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);


        // When and Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/" + expectedProduct.id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Product actualProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void deleteProductById_whenNoSuchProduct_thenThrow() throws Exception {
        // Given
        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/products/10"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        ErrorMessage errorMessage = objectMapper.readValue(result.getResponse().getContentAsString(),ErrorMessage.class);

        // Then
        assertEquals("uri=/api/products/10", errorMessage.apiPath());
        assertEquals(HttpStatus.BAD_REQUEST, errorMessage.errorCode());
        assertEquals("Product with ID 10 does not exist", errorMessage.errorMsg());
    }

    @Test
    void deleteProductById_whenSuchProduct_thenDelete() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);

        // When & Then

        mvc.perform(MockMvcRequestBuilders.delete("/api/products/" + expectedProduct.id()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/products/" + expectedProduct.id()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        ErrorMessage errorMessage = objectMapper.readValue(result.getResponse().get,ErrorMessage.class);

        assertEquals("uri=/api/products/" + expectedProduct.id(), errorMessage.apiPath());
        assertEquals(HttpStatus.BAD_REQUEST, errorMessage.errorCode());
        assertEquals("Product with ID " + expectedProduct.id() + " does not exist", errorMessage.errorMsg());
    }

    @Test
    void getProductInCriticalStock_shouldReturnProductsInCriticalStock() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 15);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();

        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/critical"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        // Then
        assertEquals(1, products.size());
    }

    @Test
    void getProductInCriticalStock_shouldReturnEmptyList() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();

        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/critical"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        // Then
        assertEquals(0, products.size());
    }

    @Test
    void getChangeLog_whenProductsAdded_thenGetChangeLog() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);

        ProductChangeDTO expected = new ProductChangeDTO(expectedProduct.id(), "Product added", ProductChangeType.ADD, ProductChangeStatus.DONE, null);
        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/changelog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ProductChangeDTO> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        // Then
        assertEquals(1, actual.size());
        assertEquals(expected.productId(), actual.getFirst().productId());
        assertEquals(expected.description(), actual.getFirst().description());
        assertEquals(expected.type(), actual.getFirst().type());
        assertEquals(expected.status(), actual.getFirst().status());
        assertNotNull(actual.getFirst().date());
    }
}
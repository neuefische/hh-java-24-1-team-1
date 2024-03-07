package com.github.hh.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);
        expectedProduct = expectedProduct.withName("Updated Product").withAmount(5).withDescription("Updated Description");
        String productJson = objectMapper.writeValueAsString(expectedProduct);

        // When and Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/api/products/update")
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
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
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
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
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

}
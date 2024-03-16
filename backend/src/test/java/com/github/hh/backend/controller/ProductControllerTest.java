package com.github.hh.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hh.backend.model.*;
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
        String uniqueProductNumber = "P" + System.currentTimeMillis(); // Erzeugt eine einzigartige Produktnummer
        ProductDTO productDTO = new ProductDTO("Product", 10, "Description", 5);
        String productDTOJson = objectMapper.writeValueAsString(productDTO);

        MvcResult setupResult = mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTOJson))
                .andReturn();
        Product originalProduct = objectMapper.readValue(setupResult.getResponse().getContentAsString(), Product.class);

        // Produkt aktualisieren
        Product updatedProduct = originalProduct.withName("Updated Product").withDescription("Updated Description").withProductNumber(uniqueProductNumber);
        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);

        // When and Then
        mvc.perform(MockMvcRequestBuilders.put("/api/products/" + originalProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(updatedProductJson));
    }

    @Test
    void updateProduct_duplicateProductNumberFailure() throws Exception {
        // Given
        ProductDTO firstProductDTO = new ProductDTO("FirstProduct", 10, "Description", 5);
        ProductDTO secondProductDTO = new ProductDTO("SecondProduct", 20, "Other Description", 10);
        mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstProductDTO)))
                .andReturn();
        MvcResult setupResult = mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondProductDTO)))
                .andReturn();
        Product secondProduct = objectMapper.readValue(setupResult.getResponse().getContentAsString(), Product.class);

        // Versuche, das zweite Produkt mit der Produktnummer des ersten zu aktualisieren
        Product updatedProduct = secondProduct.withProductNumber("P1"); // 'P1' ist die Nummer des ersten Produkts
        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);

        // When and Then
        mvc.perform(MockMvcRequestBuilders.put("/api/products/" + secondProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Erwarte Status 400 Bad Request
    }

    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("R1-01-01", "Product", 10,"Description", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        String storageSpaceNameJson = objectMapper.writeValueAsString("R1-01-01");

        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceNameJson)).andReturn();

        // When and Then
        MvcResult resultJson = mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Product result = objectMapper.readValue(resultJson.getResponse().getContentAsString(), Product.class);
        assertEquals(productDTO.name(), result.name());
        assertEquals(productDTO.amount(), result.amount());
        assertEquals(productDTO.description(), result.description());
        assertNotNull(result.id());
    }

    @Test
    void getProductById_returnTestProduct_whenCalledByCorrectId() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("R1-01-01", "Product", 10,"Description", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);
        String storageSpaceDtoJson = objectMapper.writeValueAsString("R1-01-01");

        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceDtoJson)).andReturn();

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);


        // When and Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/" + expectedProduct.id()))
                .andExpect(status().isOk())
                .andReturn();
        Product actualProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void deleteProductById_whenNoSuchProduct_thenThrow() throws Exception {
        // Given
        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/products/10"))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorMessage errorMessage = objectMapper.readValue(result.getResponse().getContentAsString(),ErrorMessage.class);

        // Then
        assertEquals("Product with ID 10 does not exist", errorMessage.errorMsg());
    }

    @Test
    void deleteProductById_whenSuchProduct_thenDelete() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO(null, "Product", 10,"Description", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);
        String storageSpaceDtoJson = objectMapper.writeValueAsString("R1-01-01");

        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceDtoJson)).andReturn();

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);


        // When & Then

        mvc.perform(MockMvcRequestBuilders.delete("/api/products/" + expectedProduct.id()))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/products/" + expectedProduct.id()))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorMessage errorMessage = objectMapper.readValue(result.getResponse().getContentAsString(),ErrorMessage.class);

        assertEquals("Product with ID " + expectedProduct.id() + " does not exist", errorMessage.errorMsg());
    }

    @Test
    void getProductInCriticalStock_shouldReturnProductsInCriticalStock() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("R1-01-01", "Product", 10,"Description", 15);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);
        String storageSpaceDtoJson = objectMapper.writeValueAsString("R1-01-01");

        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceDtoJson)).andReturn();
        mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();

        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/critical"))
                .andExpect(status().isOk())
                .andReturn();

        List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        // Then
        assertEquals(1, products.size());
    }

    @Test
    void getProductInCriticalStock_shouldReturnEmptyList() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("R1-01-01", "Product", 10,"Description", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();

        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/critical"))
                .andExpect(status().isOk())
                .andReturn();

        List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        // Then
        assertEquals(0, products.size());
    }

    @Test
    void getChangeLog_whenProductsAdded_thenGetChangeLog() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("R1-01-01", "Product", 10,"Description", 5);
        String productDtoJson = objectMapper.writeValueAsString(productDTO);
        String storageSpaceDtoJson = objectMapper.writeValueAsString("R1-01-01");

        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceDtoJson)).andReturn();

        MvcResult setup = mvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productDtoJson)).andReturn();
        Product expectedProduct = objectMapper.readValue(setup.getResponse().getContentAsString(), Product.class);

        ProductChangeDTO expected = new ProductChangeDTO(List.of(expectedProduct), "Product added", ProductChangeType.ADD, ProductChangeStatus.DONE, null);
        // When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products/changelog"))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductChangeDTO> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        // Then
        assertEquals(1, actual.size());
        assertEquals(expected.products(), actual.getFirst().products());
        assertEquals(expected.description(), actual.getFirst().description());
        assertEquals(expected.type(), actual.getFirst().type());
        assertEquals(expected.status(), actual.getFirst().status());
        assertNotNull(actual.getFirst().date());
    }

    @Test
    void addBulkProducts_whenMultiple_thenAddMultiple() throws Exception {
        // Given
        ProductDTO productDTO1 = new ProductDTO("R1-01-01", "Product1", 10,"Description1", 5);
        ProductDTO productDTO2 = new ProductDTO("R1-01-01", "Product2", 10,"Description2", 5);

        String storageSpaceDtoJson1 = objectMapper.writeValueAsString("R1-01-01");
        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceDtoJson1)).andReturn();

        String storageSpaceDtoJson2 = objectMapper.writeValueAsString("R1-01-02");
        mvc.perform(MockMvcRequestBuilders.post("/api/storage").contentType(MediaType.APPLICATION_JSON).content(storageSpaceDtoJson2)).andReturn();

        // When
        mvc.perform(MockMvcRequestBuilders.post("/api/products/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(productDTO1, productDTO2)))
                )
                .andExpect(status().isOk());

        // Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andReturn();

        List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        assertEquals(2, products.size());
    }
}
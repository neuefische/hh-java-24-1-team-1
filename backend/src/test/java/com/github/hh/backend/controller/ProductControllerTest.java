package com.github.hh.backend.controller;

import com.github.hh.backend.model.Product;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MockMvc mvc;


    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        // Given
        Product product = new Product("1", "Product", 10,"Description");
        productRepo.save(product);

        // When and Then
        mvc.perform(MockMvcRequestBuilders.put("/api/products/update")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "id" : "1",
                                  "name" : "Updated Product",
                                  "amount" : 5,
                                  "description" : "Updated Description"
                                }
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                          "id" : "1",
                          "name" : "Updated Product",
                          "amount" : 5,
                            "description" : "Updated Description"
                        }
                        """
                ));
    }



}
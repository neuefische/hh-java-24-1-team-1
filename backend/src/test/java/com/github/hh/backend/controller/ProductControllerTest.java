package com.github.hh.backend.controller;

import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService mockProductService;

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        // Given
        Product product = new Product("1", "Updated Product", 5,"Updated Description");
        when(mockProductService.updateProduct(product)).thenReturn(product);

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

        verify(mockProductService, times(1)).updateProduct(product);
        verifyNoMoreInteractions(mockProductService);
    }


    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
        Product product = new Product("1", "Product", 10,"Description");
        when(mockProductService.addProduct(productDTO)).thenReturn(product);

        // When and Then
        mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "name" : "Product",
                                  "amount" : 10,
                                  "description" : "Description"
                                }
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                          "id" : "1",
                          "name" : "Product",
                          "amount" : 10,
                            "description" : "Description"
                        }
                        """
                ));

        verify(mockProductService, times(1)).addProduct(productDTO);
        verifyNoMoreInteractions(mockProductService);
    }

    @Test
    void getProductById_returnTestProduct_whenCalledByCorrectId() throws Exception {
        Product product = new Product("1", "Test-Product", 10,"example description");
        productRepo.save(product);

        // When and Then
        mvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                          "id" : "1",
                          "name" : "Test-Product",
                          "amount" : 10,
                          "description" : "example description"
                        }
                        """
                ));

    }
}
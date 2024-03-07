package com.github.hh.backend.service;

import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductServiceTest {
    @Autowired
    private ProductRepo mockProductRepo;

    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID(){
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
        Product expected = new Product("1", "Product", 10,"Description");

        // When
        ProductService productService = new ProductService(mockProductRepo);
        Product actual = productService.addProduct(productDTO);

        // Then
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.amount(), actual.amount());
        assertEquals(expected.description(), actual.description());
        assertNotNull(actual.id());
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
        ProductService productService = new ProductService(mockProductRepo);
        Product expected = productService.addProduct(productDTO);

        // When
        Product actual = productService.updateProduct(expected.withAmount(5).withName("Updated Name").withDescription("Updated Description"));

        expected = expected.withAmount(5).withName("Updated Name").withDescription("Updated Description");
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getProductById_shouldReturnProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
        ProductService productService = new ProductService(mockProductRepo);
        Product expected = productService.addProduct(productDTO);

        // When
        Product actual = productService.getProductById(expected.id());

        // Then
        assertEquals(expected, actual);
    }
}
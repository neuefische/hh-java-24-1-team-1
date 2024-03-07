package com.github.hh.backend.service;

import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductServiceTest {
    private final ProductRepo mockProductRepo = mock(ProductRepo.class);

    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID(){
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description");
        Product expectedOutput = new Product("1", "Product", 10,"Description");
        Product expectedInput = new Product(null, "Product", 10,"Description");
        when(mockProductRepo.save(expectedInput)).thenReturn(expectedOutput);

        // When
        ProductService productService = new ProductService(mockProductRepo);

        // Then
        assertEquals(expectedOutput, productService.addProduct(productDTO));
        verify(mockProductRepo, times(1)).save(expectedInput);
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        // Given
        Product product = new Product("1", "Product", 5,"Description");
        when(mockProductRepo.save(product)).thenReturn(product);

        // When
        ProductService productService = new ProductService(mockProductRepo);

        // Then
        assertEquals(product, productService.updateProduct(product));
        verify(mockProductRepo, times(1)).save(product);
        verifyNoMoreInteractions(mockProductRepo);
    }
}
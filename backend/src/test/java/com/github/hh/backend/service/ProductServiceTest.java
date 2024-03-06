package com.github.hh.backend.service;

import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

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
        Product expected = new Product("1", "Product", 10,"Description");
        when(mockProductRepo.save(expected)).thenReturn(expected);
        when(mockProductRepo.findAll()).thenReturn(new ArrayList<>());
        // When
        ProductService productService = new ProductService(mockProductRepo);

        // Then
        assertEquals(expected, productService.addProduct(productDTO));
        verify(mockProductRepo, times(1)).save(expected);
        verify(mockProductRepo, times(1)).findAll();
        verifyNoMoreInteractions(mockProductRepo);
    }
}
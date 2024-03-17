package com.github.hh.backend.service;

import com.github.hh.backend.model.ProductId;
import com.github.hh.backend.repository.ProductIdRepo;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductIdServiceTest {

    private final ProductIdRepo mockProductIdRepo = mock(ProductIdRepo.class);

    private final ProductIdService productIdService = new ProductIdService(mockProductIdRepo);
    @Test
    void generateProductId_whenAsked_thenReturnsAndIncreases() {
        // Given
        ProductId expectedSave = new ProductId("GenericProductIdCounter", 1);
        String expectedId = "P1";
        ProductId given = new ProductId("GenericProductIdCounter", 0);

        // When
        when(mockProductIdRepo.findById("GenericProductIdCounter")).thenReturn(Optional.of(given));
        String actual = productIdService.generateProductId();

        // Then
        assertEquals(expectedId, actual);
        verify(mockProductIdRepo).findById("GenericProductIdCounter");
        verify(mockProductIdRepo).save(expectedSave);
        verifyNoMoreInteractions(mockProductIdRepo);
    }
}
package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductChangeException;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductChange;
import com.github.hh.backend.model.ProductChangeStatus;
import com.github.hh.backend.model.ProductChangeType;
import com.github.hh.backend.repository.ProductChangeRepo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductChangeServiceTest {
    private final ProductChangeRepo mockProductChangeRepo = mock(ProductChangeRepo.class);

    private final ProductChangeService productChangeService = new ProductChangeService(mockProductChangeRepo);

    @Test
    void createChange() {
        // Given
        Product product = new Product("1", "Product", 10, "Description", "123", 5);
        ProductChange expected = new ProductChange("New Change Id",
                List.of(product),
                "Create",
                ProductChangeType.ADD,
                ProductChangeStatus.ERROR,
                Instant.ofEpochMilli(0)
        );

        // When
        when(mockProductChangeRepo.save(any(ProductChange.class)))
                .thenReturn(expected);
        ProductChange actual = productChangeService.createChange(expected.products(), expected.description(), expected.type(), expected.status());

        // Then
        verify(mockProductChangeRepo).save(any(ProductChange.class));
        verifyNoMoreInteractions(mockProductChangeRepo);
        assertEquals(expected, actual);
    }

    @Test
    void updateProductChange_whenNoSuchChange_thenThrow() {
        // Given
        Product product = new Product("1", "Product", 10, "Description", "123", 5);
        ProductChange productChange = new ProductChange("New Change Id",
                List.of(product),
                "Create",
                ProductChangeType.ADD,
                ProductChangeStatus.ERROR,
                Instant.ofEpochMilli(0)
        );

        // When
        when(mockProductChangeRepo.existsById(productChange.id())).thenReturn(false);

        // Then
        assertThrows(NoSuchProductChangeException.class, () -> productChangeService.updateProductChange(productChange));
        verify(mockProductChangeRepo).existsById(productChange.id());
        verifyNoMoreInteractions(mockProductChangeRepo);
    }

    @Test
    void updateProductChange_whenSuchChange_thenChange() {
        // Given
        Product product = new Product("1", "Product", 10, "Description", "123", 5);
        ProductChange expected = new ProductChange("New Change Id",
                List.of(product),
                "Create",
                ProductChangeType.ADD,
                ProductChangeStatus.ERROR,
                Instant.ofEpochMilli(0)
        );

        // When
        when(mockProductChangeRepo.existsById(expected.id())).thenReturn(true);
        when(mockProductChangeRepo.save(expected)).thenReturn(expected);
        ProductChange actual = productChangeService.updateProductChange(expected);

        // Then
        verify(mockProductChangeRepo).existsById(expected.id());
        verify(mockProductChangeRepo).save(expected);
        verifyNoMoreInteractions(mockProductChangeRepo);
        assertEquals(expected, actual);
    }

    @Test
    void getChangeLog_thenReturnTheLog() {
        // Given
        Product product1 = new Product("1", "Product", 10, "Description", "123", 5);
        Product product2 = new Product("2", "Product", 10, "Description", "123", 5);
        List<ProductChange> expected = List.of(
                new ProductChange("1", List.of(product1), "Create", ProductChangeType.ADD, ProductChangeStatus.ERROR, Instant.now()),
                new ProductChange("2", List.of(product2), "Update", ProductChangeType.UPDATE, ProductChangeStatus.DONE, Instant.now())
        );


        //When
        when(mockProductChangeRepo.findAll()).thenReturn(expected);
        List<ProductChange> actual = productChangeService.getChangeLog();

        // Then
        verify(mockProductChangeRepo).findAll();
        verifyNoMoreInteractions(mockProductChangeRepo);
        assertEquals(expected, actual);
    }
}
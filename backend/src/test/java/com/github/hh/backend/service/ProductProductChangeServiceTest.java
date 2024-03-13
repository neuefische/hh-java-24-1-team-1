package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductChangeException;
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

class ProductProductChangeServiceTest {
    private final ProductChangeRepo mockProductChangeRepo = mock(ProductChangeRepo.class);

    private final ProductChangeService productChangeService = new ProductChangeService(mockProductChangeRepo);

    @Test
    void createChange() {
        // Given
        ProductChange expected = new ProductChange("New Change Id",
                "1",
                "Create",
                ProductChangeType.ADD,
                ProductChangeStatus.ERROR,
                Instant.ofEpochMilli(0)
        );

        // When
        when(mockProductChangeRepo.save(any(ProductChange.class)))
                .thenReturn(expected);
        String actual = productChangeService.createChange(expected.productId(), expected.description(), expected.type(), expected.status());

        // Then
        verify(mockProductChangeRepo).save(any(ProductChange.class));
        verifyNoMoreInteractions(mockProductChangeRepo);
        assertEquals(expected.id(), actual);
    }

    @Test
    void updateChangeStatus_whenNoSuchChange_thenThrow() {
        // Given
        String changeId = "1";
        ProductChangeStatus status = ProductChangeStatus.DONE;

        // When
        when(mockProductChangeRepo.existsById(changeId)).thenReturn(false);

        // Then
        assertThrows(NoSuchProductChangeException.class, () -> productChangeService.updateChangeStatus(changeId, status));
        verify(mockProductChangeRepo).existsById(changeId);
        verifyNoMoreInteractions(mockProductChangeRepo);
    }

    @Test
    void updateChangeStatus_whenSuchChange_thenChange() {
        // Given
        String changeId = "1";
        ProductChangeStatus status = ProductChangeStatus.DONE;
        ProductChange productChange = new ProductChange(changeId, "1", "Create", ProductChangeType.ADD, ProductChangeStatus.ERROR, Instant.now());

        // When
        when(mockProductChangeRepo.existsById(changeId)).thenReturn(true);
        when(mockProductChangeRepo.findById(changeId)).thenReturn(java.util.Optional.of(productChange));
        when(mockProductChangeRepo.save(productChange.withStatus(status))).thenReturn(productChange.withStatus(status));
        ProductChange actual = productChangeService.updateChangeStatus(changeId, status);

        // Then
        verify(mockProductChangeRepo).existsById(changeId);
        verify(mockProductChangeRepo).findById(changeId);
        verify(mockProductChangeRepo).save(productChange.withStatus(status));
        verifyNoMoreInteractions(mockProductChangeRepo);
        assertEquals(status, actual.status());
    }

    @Test
    void updateChangeProductId_whenNoSuchChange_thenThrow() {
        // Given
        String changeId = "1";
        String productId = "1";

        // When
        when(mockProductChangeRepo.existsById(changeId)).thenReturn(false);

        // Then
        assertThrows(NoSuchProductChangeException.class, () -> productChangeService.updateChangeProductId(changeId, productId));
        verify(mockProductChangeRepo).existsById(changeId);
        verifyNoMoreInteractions(mockProductChangeRepo);
    }

    @Test
    void updateChangeProductId_whenSuchChange_thenChange() {
        // Given
        String changeId = "1";
        String productId = "1";
        ProductChange productChange = new ProductChange(changeId, "1", "Create", ProductChangeType.ADD, ProductChangeStatus.ERROR, Instant.now());

        // When
        when(mockProductChangeRepo.existsById(changeId)).thenReturn(true);
        when(mockProductChangeRepo.findById(changeId)).thenReturn(java.util.Optional.of(productChange));
        when(mockProductChangeRepo.save(productChange.withProductId(productId))).thenReturn(productChange.withProductId(productId));
        ProductChange actual = productChangeService.updateChangeProductId(changeId, productId);

        // Then
        verify(mockProductChangeRepo).existsById(changeId);
        verify(mockProductChangeRepo).findById(changeId);
        verify(mockProductChangeRepo).save(productChange.withProductId(productId));
        verifyNoMoreInteractions(mockProductChangeRepo);
        assertEquals(productId, actual.productId());
    }

    @Test
    void getChangeLog_thenReturnTheLog() {
        // Given
        List<ProductChange> expected = List.of(
                new ProductChange("1", "1", "Create", ProductChangeType.ADD, ProductChangeStatus.ERROR, Instant.now()),
                new ProductChange("2", "2", "Update", ProductChangeType.UPDATE, ProductChangeStatus.DONE, Instant.now())
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
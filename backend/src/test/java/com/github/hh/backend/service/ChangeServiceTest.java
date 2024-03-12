package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchChangeException;
import com.github.hh.backend.model.Change;
import com.github.hh.backend.model.ChangeStatus;
import com.github.hh.backend.model.ChangeType;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.repository.ChangeRepo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeServiceTest {
    private final ChangeRepo mockChangeRepo = mock(ChangeRepo.class);

    private final ChangeService changeService = new ChangeService(mockChangeRepo);

    @Test
    void createChange() {
        // Given
        Change expected = new Change("New Change Id",
                "1",
                "Create",
                ChangeType.ADD,
                ChangeStatus.ERROR,
                Instant.ofEpochMilli(0)
        );

        // When
        when(mockChangeRepo.save(any(Change.class)))
                .thenReturn(expected);
        String actual = changeService.createChange(expected.productId(), expected.description(), expected.type(), expected.status());

        // Then
        verify(mockChangeRepo).save(any(Change.class));
        verifyNoMoreInteractions(mockChangeRepo);
        assertEquals(expected.id(), actual);
    }

    @Test
    void updateChangeStatus_whenNoSuchChange_thenThrow() {
        // Given
        String changeId = "1";
        ChangeStatus status = ChangeStatus.DONE;

        // When
        when(mockChangeRepo.existsById(changeId)).thenReturn(false);

        // Then
        assertThrows(NoSuchChangeException.class, () -> changeService.updateChangeStatus(changeId, status));
        verify(mockChangeRepo).existsById(changeId);
        verifyNoMoreInteractions(mockChangeRepo);
    }

    @Test
    void updateChangeStatus_whenSuchChange_thenChange() {
        // Given
        String changeId = "1";
        ChangeStatus status = ChangeStatus.DONE;
        Change change = new Change(changeId, "1", "Create", ChangeType.ADD, ChangeStatus.ERROR, Instant.now());

        // When
        when(mockChangeRepo.existsById(changeId)).thenReturn(true);
        when(mockChangeRepo.findById(changeId)).thenReturn(java.util.Optional.of(change));
        when(mockChangeRepo.save(change.withStatus(status))).thenReturn(change.withStatus(status));
        Change actual = changeService.updateChangeStatus(changeId, status);

        // Then
        verify(mockChangeRepo).existsById(changeId);
        verify(mockChangeRepo).findById(changeId);
        verify(mockChangeRepo).save(change.withStatus(status));
        verifyNoMoreInteractions(mockChangeRepo);
        assertEquals(status, actual.status());
    }

    @Test
    void updateChangeProductId_whenNoSuchChange_thenThrow() {
        // Given
        String changeId = "1";
        String productId = "1";

        // When
        when(mockChangeRepo.existsById(changeId)).thenReturn(false);

        // Then
        assertThrows(NoSuchChangeException.class, () -> changeService.updateChangeProductId(changeId, productId));
        verify(mockChangeRepo).existsById(changeId);
        verifyNoMoreInteractions(mockChangeRepo);
    }

    @Test
    void updateChangeProductId_whenSuchChange_thenChange() {
        // Given
        String changeId = "1";
        String productId = "1";
        Change change = new Change(changeId, "1", "Create", ChangeType.ADD, ChangeStatus.ERROR, Instant.now());

        // When
        when(mockChangeRepo.existsById(changeId)).thenReturn(true);
        when(mockChangeRepo.findById(changeId)).thenReturn(java.util.Optional.of(change));
        when(mockChangeRepo.save(change.withProductId(productId))).thenReturn(change.withProductId(productId));
        Change actual = changeService.updateChangeProductId(changeId, productId);

        // Then
        verify(mockChangeRepo).existsById(changeId);
        verify(mockChangeRepo).findById(changeId);
        verify(mockChangeRepo).save(change.withProductId(productId));
        verifyNoMoreInteractions(mockChangeRepo);
        assertEquals(productId, actual.productId());
    }

    @Test
    void getChangeLog_thenReturnTheLog() {
        // Given
        List<Change> expected = List.of(
                new Change("1", "1", "Create", ChangeType.ADD, ChangeStatus.ERROR, Instant.now()),
                new Change("2", "2", "Update", ChangeType.UPDATE, ChangeStatus.DONE, Instant.now())
        );


        //When
        when(mockChangeRepo.findAll()).thenReturn(expected);
        List<Change> actual = changeService.getChangeLog();

        // Then
        verify(mockChangeRepo).findAll();
        verifyNoMoreInteractions(mockChangeRepo);
        assertEquals(expected, actual);
    }
}
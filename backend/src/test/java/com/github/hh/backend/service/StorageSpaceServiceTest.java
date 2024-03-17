package com.github.hh.backend.service;

import com.github.hh.backend.exception.DuplicateStorageSpaceNameException;
import com.github.hh.backend.exception.NoEmptyStorageSpaceException;
import com.github.hh.backend.exception.NoSuchStorageSpaceException;
import com.github.hh.backend.model.StorageSpace;
import com.github.hh.backend.repository.StorageSpaceRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageSpaceServiceTest {
    private final StorageSpaceRepo mockStorageSpaceRepo = mock(StorageSpaceRepo.class);

    private final StorageSpaceService storageSpaceService = new StorageSpaceService(mockStorageSpaceRepo);

    @Test
    void getNewStorageSpaceName_whenAvailable_thenReturn() {
        // Given
        String expected = "storageSpaceName";

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(List.of(new StorageSpace(null, expected, false)));
        String actual = storageSpaceService.getNewStorageSpaceName();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getNewStorageSpaceName_whenNoAvailable_thenThrow() {
        // Given

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(List.of(new StorageSpace(null, "storageSpaceName", true)));

        // Then
        assertThrows(NoEmptyStorageSpaceException.class, storageSpaceService::getNewStorageSpaceName);
    }

    @Test
    void toggleStorageSpaceOccupationByName_whenEmptyList_thenThrow() {
        // Given
        String storageSpaceName = "storageSpaceName";

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(List.of());

        // Then
        assertThrows(NoSuchStorageSpaceException.class, () -> storageSpaceService.toggleStorageSpaceOccupationByName(storageSpaceName));
    }

    @Test
    void toggleStorageSpaceOccupationByName_whenMultipleSuchSpaces_thenThrow() {
        // Given
        String storageSpaceName = "storageSpaceName";
        StorageSpace storageSpace1 = new StorageSpace("1L", storageSpaceName, false);
        StorageSpace storageSpace2 = new StorageSpace("2L", storageSpaceName, false);

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(List.of(storageSpace1, storageSpace2));

        // Then
        assertThrows(DuplicateStorageSpaceNameException.class, () -> storageSpaceService.toggleStorageSpaceOccupationByName(storageSpaceName));
    }

    @Test
    void toggleStorageSpaceOccupationByName_whenSuchStorageSpaceUnoccupied_thenOccupy() {
        // Given
        StorageSpace expected = new StorageSpace("1L", "storageSpaceName", false);

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(List.of(expected));
        when(mockStorageSpaceRepo.save(expected.withOccupied(true))).thenReturn(expected.withOccupied(true));
        StorageSpace actual = storageSpaceService.toggleStorageSpaceOccupationByName(expected.storageSpaceName());

        // Then
        assertEquals(expected.withOccupied(true), actual);
        verify(mockStorageSpaceRepo).save(expected.withOccupied(true));
        verify(mockStorageSpaceRepo).findAll();
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }

    @Test
    void toggleStorageSpaceOccupationByName_whenSuchStorageSpaceOccupied_thenUnOccupy() {
        // Given
        StorageSpace expected = new StorageSpace("1L", "storageSpaceName", true);

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(List.of(expected));
        when(mockStorageSpaceRepo.save(expected.withOccupied(false))).thenReturn(expected.withOccupied(false));
        StorageSpace actual = storageSpaceService.toggleStorageSpaceOccupationByName(expected.storageSpaceName());

        // Then
        assertEquals(expected.withOccupied(false), actual);
        verify(mockStorageSpaceRepo).save(expected.withOccupied(false));
        verify(mockStorageSpaceRepo).findAll();
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }

    @Test
    void getAllStorageSpaces_whenSomething_thenReturnSomething() {
        // Given
        List<StorageSpace> expected = List.of(new StorageSpace("1L", "storageSpaceName", false));

        // When
        when(mockStorageSpaceRepo.findAll()).thenReturn(expected);
        List<StorageSpace> actual = storageSpaceService.getAllStorageSpaces();

        // Then
        assertEquals(expected, actual);
        verify(mockStorageSpaceRepo).findAll();
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }

    @Test
    void addNewStorageSpace_whenUnique_thenAdd() {
        // Given
        StorageSpace expected = new StorageSpace("1L", "storageSpaceName", false);

        // When
        when(mockStorageSpaceRepo.save(expected.withId(null))).thenReturn(expected);
        when(mockStorageSpaceRepo.existsByStorageSpaceName(expected.storageSpaceName())).thenReturn(false);
        StorageSpace actual = storageSpaceService.addNewStorageSpace(expected.storageSpaceName());

        // Then
        assertEquals(expected, actual);
        verify(mockStorageSpaceRepo).save(expected.withId(null));
        verify(mockStorageSpaceRepo).existsByStorageSpaceName(expected.storageSpaceName());
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }

    @Test
    void addNewStorageSpace_whenTaken_thenThrow() {
        // Given
        StorageSpace expected = new StorageSpace("1L", "storageSpaceName", false);

        // When
        when(mockStorageSpaceRepo.save(expected.withId(null))).thenReturn(expected);
        when(mockStorageSpaceRepo.existsByStorageSpaceName(expected.storageSpaceName())).thenReturn(true);

        // Then
        assertThrows(DuplicateStorageSpaceNameException.class, () -> storageSpaceService.addNewStorageSpace("storageSpaceName"));
        verify(mockStorageSpaceRepo).existsByStorageSpaceName(expected.storageSpaceName());
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }

    @Test
    void toggleStorageSpaceOccupation_whenSuchStorageSpaceUnoccupied_thenOccupy() {
        // Given
        String id = "1L";
        StorageSpace expected = new StorageSpace(id, "storageSpaceName", false);

        // When
        when(mockStorageSpaceRepo.findById(id)).thenReturn(java.util.Optional.of(expected));
        when(mockStorageSpaceRepo.save(expected.withOccupied(true))).thenReturn(expected.withOccupied(true));
        StorageSpace actual = storageSpaceService.toggleStorageSpaceOccupation(id);

        // Then
        assertEquals(expected.withOccupied(true), actual);
        verify(mockStorageSpaceRepo).findById(id);
        verify(mockStorageSpaceRepo).save(expected.withOccupied(true));
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }

    @Test
    void toggleStorageSpaceOccupation_whenSuchStorageSpaceOccupied_thenUnOccupy() {
        // Given
        String id = "1L";
        StorageSpace expected = new StorageSpace(id, "storageSpaceName", true);

        // When
        when(mockStorageSpaceRepo.findById(id)).thenReturn(java.util.Optional.of(expected));
        when(mockStorageSpaceRepo.save(expected.withOccupied(false))).thenReturn(expected.withOccupied(false));
        StorageSpace actual = storageSpaceService.toggleStorageSpaceOccupation(id);

        // Then
        assertEquals(expected.withOccupied(false), actual);
        verify(mockStorageSpaceRepo).findById(id);
        verify(mockStorageSpaceRepo).save(expected.withOccupied(false));
        verifyNoMoreInteractions(mockStorageSpaceRepo);
    }
}
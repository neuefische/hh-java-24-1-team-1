package com.github.hh.backend.service;

import com.github.hh.backend.model.StorageSpace;
import com.github.hh.backend.repository.StorageSpaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.hh.backend.exception.NoEmptyStorageSpaceException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageSpaceService {
    private final StorageSpaceRepo storageSpaceRepo;
    public String getNewStorageSpace() throws NoEmptyStorageSpaceException {
        return storageSpaceRepo.findAll().stream()
                .filter(StorageSpace::isOccupied)
                .findFirst()
                .orElseThrow(NoEmptyStorageSpaceException::new)
                .id();
    }

    public void occupyStorageSpace(String id) {
        storageSpaceRepo.save(storageSpaceRepo.findById(id).orElseThrow().withOccupied(true));
    }

    public void freeStorageSpace(String id) {
        storageSpaceRepo.save(storageSpaceRepo.findById(id).orElseThrow().withOccupied(false));
    }

    public List<String> getAllStorageSpaces() {
        return storageSpaceRepo.findAll().stream()
                .map(StorageSpace::id)
                .toList();
    }

    public List<String> getEmptyStorageSpaces() {
        return storageSpaceRepo.findAll().stream()
                .filter(s -> !s.isOccupied())
                .map(StorageSpace::id)
                .toList();
    }

    public List<String> getOccupiedStorageSpaces() {
        return storageSpaceRepo.findAll().stream()
                .filter(StorageSpace::isOccupied)
                .map(StorageSpace::id)
                .toList();
    }

    public int getOccupiedStorageSpacesCount() {
        return (int) storageSpaceRepo.findAll().stream()
                .filter(StorageSpace::isOccupied)
                .count();
    }

    public String addNewStorageSpace(String storageSpaceId) {
        return storageSpaceRepo.save(new StorageSpace(null, storageSpaceId, false)).storageSpaceId();
    }
}

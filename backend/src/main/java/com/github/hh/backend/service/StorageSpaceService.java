package com.github.hh.backend.service;

import com.github.hh.backend.model.StorageSpace;
import com.github.hh.backend.model.StorageSpaceDTO;
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

    public List<StorageSpaceDTO> getAllStorageSpaces() {
        return storageSpaceRepo.findAll().stream()
                .map(storageSpace -> new StorageSpaceDTO(storageSpace.id(), storageSpace.isOccupied()))
                .toList();
    }

    public StorageSpaceDTO addNewStorageSpace(String storageSpaceId) {
        StorageSpace newStorageSpace = storageSpaceRepo.save(new StorageSpace(null, storageSpaceId, false));
        return new StorageSpaceDTO(newStorageSpace.id(), newStorageSpace.isOccupied());
    }

    public void toggleStorageSpaceOccupation(String storageSpaceId) {
        StorageSpace storageSpace = storageSpaceRepo.findById(storageSpaceId).orElseThrow();
        storageSpaceRepo.save(storageSpace.withOccupied(!storageSpace.isOccupied()));
    }
}

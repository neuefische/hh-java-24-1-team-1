package com.github.hh.backend.service;

import com.github.hh.backend.model.StorageSpace;
import com.github.hh.backend.repository.StorageSpaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.hh.backend.exception.NoEmptyStorageSpaceException;

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
}

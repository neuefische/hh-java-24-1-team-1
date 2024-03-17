package com.github.hh.backend.service;

import com.github.hh.backend.exception.DuplicateStorageSpaceNameException;
import com.github.hh.backend.exception.NoSuchStorageSpaceException;
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
    public String getNewStorageSpaceName() {
        List<StorageSpace> emptyStorageSpaces = storageSpaceRepo.findAll().stream()
                .filter(space -> !space.isOccupied())
                .toList();

        if(emptyStorageSpaces.isEmpty()) {
            throw new NoEmptyStorageSpaceException();
        }

        return emptyStorageSpaces.getFirst().storageSpaceName();
    }

    public StorageSpace toggleStorageSpaceOccupationByName(String storageSpaceName) {
        List<StorageSpace> storageSpaceList = storageSpaceRepo.findAll().stream()
                .filter(storageSpace -> storageSpace.storageSpaceName().equals(storageSpaceName)).toList();

        if(storageSpaceList.isEmpty()) {
            throw new NoSuchStorageSpaceException(storageSpaceName);
        }
        if(storageSpaceList.size() > 1) {
            throw new DuplicateStorageSpaceNameException(storageSpaceName);
        }

        StorageSpace storageSpace = storageSpaceList.getFirst();

        return storageSpaceRepo.save(storageSpace.withOccupied(!storageSpace.isOccupied()));
    }

    public List<StorageSpace> getAllStorageSpaces() {
        return storageSpaceRepo.findAll();
    }

    public StorageSpace addNewStorageSpace(String storageSpaceName) {
        if(storageSpaceRepo.existsByStorageSpaceName(storageSpaceName)) {
            throw new DuplicateStorageSpaceNameException(storageSpaceName);
        }
        return storageSpaceRepo.save(new StorageSpace(null, storageSpaceName, false));
    }

    public StorageSpace toggleStorageSpaceOccupation(String id) {
        StorageSpace storageSpace = storageSpaceRepo.findById(id).orElseThrow(() -> new NoSuchStorageSpaceException(id));
        return storageSpaceRepo.save(storageSpace.withOccupied(!storageSpace.isOccupied()));
    }

    public StorageSpace getStorageSpaceById(String id) {
        return storageSpaceRepo.findById(id).orElseThrow(() -> new NoSuchStorageSpaceException(id));
    }
}

package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchStorageSpaceException;
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
        List<StorageSpace> emptyStorageSpaces = storageSpaceRepo.findAll().stream()
                .filter(space -> !space.isOccupied())
                .toList();

        if(emptyStorageSpaces.isEmpty()) {
            throw new NoEmptyStorageSpaceException();
        }

        return emptyStorageSpaces.getFirst().storageSpaceName();
    }

    public void occupyStorageSpace(String storageSpaceName) {
        storageSpaceRepo.save(storageSpaceRepo.findById(getStorageSpaceByStorageSpaceName(storageSpaceName).id()).orElseThrow(() -> new NoSuchStorageSpaceException(storageSpaceName)).withOccupied(true));
    }

    public void freeStorageSpace(String storageSpaceName) {
        storageSpaceRepo.save(storageSpaceRepo.findById(getStorageSpaceByStorageSpaceName(storageSpaceName).id()).orElseThrow().withOccupied(false));
    }

    private StorageSpace getStorageSpaceByStorageSpaceName(String storageSpaceName) {
        List<StorageSpace> storageSpaceList = storageSpaceRepo.findAll().stream()
                .filter(storageSpace -> storageSpace.storageSpaceName().equals(storageSpaceName)).toList();

        if(storageSpaceList.isEmpty()) {
            throw new NoSuchStorageSpaceException(storageSpaceName);
        }
        if(storageSpaceList.size() > 1) {
            throw new IllegalStateException("Multiple storage spaces with the same name");
        }

        return storageSpaceList.getFirst();
    }

    public List<StorageSpace> getAllStorageSpaces() {
        return storageSpaceRepo.findAll();
    }

    public StorageSpaceDTO addNewStorageSpace(String id) {
        StorageSpace newStorageSpace = storageSpaceRepo.save(new StorageSpace(null, id, false));
        return new StorageSpaceDTO(newStorageSpace.storageSpaceName(), newStorageSpace.isOccupied());
    }

    public void toggleStorageSpaceOccupation(String id) {
        StorageSpace storageSpace = storageSpaceRepo.findById(id).orElseThrow();
        storageSpaceRepo.save(storageSpace.withOccupied(!storageSpace.isOccupied()));
    }
}

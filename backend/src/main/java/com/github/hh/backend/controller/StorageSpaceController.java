package com.github.hh.backend.controller;

import com.github.hh.backend.model.StorageSpaceDTO;
import com.github.hh.backend.service.StorageSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageSpaceController {
    private final StorageSpaceService storageSpaceService;

    @GetMapping
    public List<StorageSpaceDTO> getAllStorageSpaces() {
        return storageSpaceService.getAllStorageSpaces();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StorageSpaceDTO addNewStorageSpace(@RequestBody String storageSpaceId){
        return storageSpaceService.addNewStorageSpace(storageSpaceId);
    }

    @PutMapping
    public void toggleStorageSpaceOccupation(@RequestBody String storageSpaceId){
        storageSpaceService.toggleStorageSpaceOccupation(storageSpaceId);
    }

}

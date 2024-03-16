package com.github.hh.backend.controller;

import com.github.hh.backend.model.StorageSpace;
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
    public List<StorageSpace> getAllStorageSpaces() {
        return storageSpaceService.getAllStorageSpaces();
    }

    @GetMapping("/{id}")
    public StorageSpace getStorageSpaceById(@PathVariable String id) {
        return storageSpaceService.getStorageSpaceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StorageSpace addNewStorageSpace(@RequestBody String storageSpaceName){
        return storageSpaceService.addNewStorageSpace(storageSpaceName);
    }

    @PutMapping
    public StorageSpace toggleStorageSpaceOccupation(@RequestBody String id){
        return storageSpaceService.toggleStorageSpaceOccupation(id);
    }

}

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StorageSpaceDTO addNewStorageSpace(@RequestBody String id){
        return storageSpaceService.addNewStorageSpace(id);
    }

    @PutMapping
    public void toggleStorageSpaceOccupation(@RequestBody String id){
        storageSpaceService.toggleStorageSpaceOccupation(id);
    }

}

package com.github.hh.backend.controller;

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
    public List<String> getAllStorageSpaces() {
        return storageSpaceService.getAllStorageSpaces();
    }

    @GetMapping("/empty")
    public List<String> getEmptyStorageSpaces() {
        return storageSpaceService.getEmptyStorageSpaces();
    }

    @GetMapping("/occupied")
    public List<String> getOccupiedStorageSpaces() {
        return storageSpaceService.getOccupiedStorageSpaces();
    }

    @GetMapping("/occupied/count")
    public int getOccupiedStorageSpacesCount() {
        return storageSpaceService.getOccupiedStorageSpacesCount();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addNewStorageSpace(@RequestBody String storageSpaceId){
        return storageSpaceService.addNewStorageSpace(storageSpaceId);
    }

}

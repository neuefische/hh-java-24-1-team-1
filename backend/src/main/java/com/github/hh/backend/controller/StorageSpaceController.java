package com.github.hh.backend.controller;

import com.github.hh.backend.service.StorageSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageSpaceController {
    private final StorageSpaceService storageSpaceService;


}

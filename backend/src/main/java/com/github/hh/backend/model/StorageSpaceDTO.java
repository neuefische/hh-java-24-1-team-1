package com.github.hh.backend.model;

public record StorageSpaceDTO(
        String storageSpaceName,
        boolean isOccupied
) {
}

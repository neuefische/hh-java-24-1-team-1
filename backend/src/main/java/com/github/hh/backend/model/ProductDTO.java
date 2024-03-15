package com.github.hh.backend.model;

public record ProductDTO(
        String storageSpaceId,
        String name,
        int amount,
        String description,
        int minimumStockLevel
) {
}

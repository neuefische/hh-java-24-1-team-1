package com.github.hh.backend.model;

public record ProductDTO(
        String storageSpaceName,
        String name,
        int amount,
        String description,
        int minimumStockLevel
) {
}

package com.github.hh.backend.model;

import java.time.Instant;

public record ProductChangeDTO(
        Product[] products,
        String description,
        ProductChangeType type,
        ProductChangeStatus status,
        Instant date
) {
}

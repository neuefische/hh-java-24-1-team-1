package com.github.hh.backend.model;

import java.time.Instant;

public record ChangeDTO(
        String productId,
        String description,
        ChangeType type,
        ChangeStatus status,
        Instant date
) {
}

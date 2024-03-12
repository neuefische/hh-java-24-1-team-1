package com.github.hh.backend.model;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public record ChangeDTO(
        String productId,
        String description,
        ChangeType type,
        ChangeStatus status,
        Instant date
) {
}

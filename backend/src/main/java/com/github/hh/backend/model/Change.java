package com.github.hh.backend.model;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public record Change(
        @Id
        String id,
        String productId,
        String description,
        ChangeType type,
        ChangeStatus status,
        Instant date
) {

}

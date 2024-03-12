package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@With
public record Change(
        @Id
        String id,
        String productId,
        ChangeStatus status,
        ChangeType type,
        String description,
        Instant date
) {

}

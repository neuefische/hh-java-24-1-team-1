package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

@With
public record StorageSpace(
        @Id
        String id,
        String storageSpaceName,
        boolean isOccupied

) {
}

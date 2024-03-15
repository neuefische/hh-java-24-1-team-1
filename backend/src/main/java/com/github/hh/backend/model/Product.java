package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

@With
public record Product (
        @Id
        String id,
        String storageSpaceId,
        String name,
        int amount,
        String description,
        String productNumber,
        int minimumStockLevel
){
}

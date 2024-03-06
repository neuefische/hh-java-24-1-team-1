package com.github.hh.backend.model;

import lombok.With;

@With
public record Product (
        String id,
        String name,
        int amount
){
}

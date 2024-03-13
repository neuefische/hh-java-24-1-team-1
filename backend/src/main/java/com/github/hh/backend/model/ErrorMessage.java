package com.github.hh.backend.model;

import java.time.LocalDateTime;

public record ErrorMessage(
        String errorMsg,
        LocalDateTime errorTime
) {
}

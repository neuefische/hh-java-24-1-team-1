package com.github.hh.backend.exception;

import java.util.NoSuchElementException;

public class NoSuchChangeException extends NoSuchElementException {
    public NoSuchChangeException(String message) {
        super(message);
    }
}

package com.github.hh.backend.exception;

import java.util.NoSuchElementException;

public class NoSuchProductChangeException extends NoSuchElementException {
    public NoSuchProductChangeException(String message) {
        super(message);
    }
}

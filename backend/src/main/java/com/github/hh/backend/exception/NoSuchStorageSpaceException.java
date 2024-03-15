package com.github.hh.backend.exception;

import java.util.NoSuchElementException;

public class NoSuchStorageSpaceException extends NoSuchElementException {
    public NoSuchStorageSpaceException(String message) {
        super(message);
    }
}

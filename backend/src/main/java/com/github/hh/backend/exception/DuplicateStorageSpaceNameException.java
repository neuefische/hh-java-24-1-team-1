package com.github.hh.backend.exception;

import java.util.NoSuchElementException;

public class DuplicateStorageSpaceNameException extends NoSuchElementException {
    public DuplicateStorageSpaceNameException(String message) {
        super(message);
    }
}

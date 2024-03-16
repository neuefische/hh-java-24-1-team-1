package com.github.hh.backend.exception;

import java.util.NoSuchElementException;

public class MultipleStorageSpacesWithTheSameNameException extends NoSuchElementException {
    public MultipleStorageSpacesWithTheSameNameException(String message) {
        super(message);
    }
}

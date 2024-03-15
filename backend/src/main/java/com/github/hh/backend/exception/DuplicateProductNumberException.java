package com.github.hh.backend.exception;

public class DuplicateProductNumberException extends RuntimeException {
    public DuplicateProductNumberException(String productNumber) {
        super("Product number " + productNumber + " already exists.");
    }
}
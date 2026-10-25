package com.example.DifferentWorlds.Exceptions;

/**
 * Base class for "the request is valid, but it conflicts with the current state of the data".
 * GlobalExceptionHandler answers every subclass with HTTP 409.
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
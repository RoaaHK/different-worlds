package com.example.DifferentWorlds.Exceptions;

public class ItemNotAvailableException extends ConflictException {

    public ItemNotAvailableException(String title) {
        super("'" + title + "' is not available in the store");
    }
}
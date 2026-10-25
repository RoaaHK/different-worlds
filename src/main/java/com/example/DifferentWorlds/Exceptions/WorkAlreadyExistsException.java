package com.example.DifferentWorlds.Exceptions;

public class WorkAlreadyExistsException extends ConflictException {

    public WorkAlreadyExistsException(String title) {
        super("A literary work already exists with title: " + title);
    }
}

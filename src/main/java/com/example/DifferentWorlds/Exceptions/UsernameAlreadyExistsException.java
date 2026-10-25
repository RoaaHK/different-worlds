package com.example.DifferentWorlds.Exceptions;

public class UsernameAlreadyExistsException extends ConflictException {

    public UsernameAlreadyExistsException(String userName) {
        super("Username already exists: " + userName);
    }
}
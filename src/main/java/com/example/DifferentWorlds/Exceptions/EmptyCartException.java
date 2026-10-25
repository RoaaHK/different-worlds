package com.example.DifferentWorlds.Exceptions;

public class EmptyCartException extends ConflictException {

    public EmptyCartException() {
        super("Cart is empty");
    }
}
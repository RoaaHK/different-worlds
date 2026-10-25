package com.example.DifferentWorlds.Exceptions;

public class InsufficientStockException extends ConflictException {

    public InsufficientStockException(int available, int requested) {
        super("Insufficient stock: available=" + available + ", requested=" + requested);
    }
}
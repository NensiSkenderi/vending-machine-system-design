package org.vending.machine.exception;

public class SoldOutException extends RuntimeException {

    private final String message;

    public SoldOutException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}

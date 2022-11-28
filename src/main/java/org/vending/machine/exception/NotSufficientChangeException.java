package org.vending.machine.exception;

public class NotSufficientChangeException extends RuntimeException {

    private final String message;

    public NotSufficientChangeException(String message) {
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

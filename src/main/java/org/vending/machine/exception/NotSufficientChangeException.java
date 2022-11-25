package org.vending.machine.exception;

public class NotSufficientChangeException extends RuntimeException {

    private String message;

    public NotSufficientChangeException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

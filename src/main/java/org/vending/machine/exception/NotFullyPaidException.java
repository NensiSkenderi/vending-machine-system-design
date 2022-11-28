package org.vending.machine.exception;

public class NotFullyPaidException extends RuntimeException {

    private final String message;

    public NotFullyPaidException(String message) {
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

package org.vending.machine.exception;

public class NotFullyPaidexception extends RuntimeException {

    private String message;

    public NotFullyPaidexception(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

package it.unict.davidemilazzo.claire.exception;

public class NotTheOwnerException extends RuntimeException {
    public NotTheOwnerException(String message) {
        super(message);
    }
}

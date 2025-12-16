package it.unict.davidemilazzo.claire.exception;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException(String message) {
        super(message);
    }
}

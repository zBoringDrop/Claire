package it.unict.davidemilazzo.claire.exception;

public class AIProviderNotFoundException extends RuntimeException {
    public AIProviderNotFoundException(String message) {
        super(message);
    }
}

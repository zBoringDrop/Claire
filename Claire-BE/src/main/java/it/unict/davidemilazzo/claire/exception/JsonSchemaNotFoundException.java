package it.unict.davidemilazzo.claire.exception;

public class JsonSchemaNotFoundException extends RuntimeException {
    public JsonSchemaNotFoundException(String message) {
        super(message);
    }
}

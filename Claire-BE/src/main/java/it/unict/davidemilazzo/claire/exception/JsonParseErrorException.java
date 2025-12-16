package it.unict.davidemilazzo.claire.exception;

public class JsonParseErrorException extends RuntimeException {
    public JsonParseErrorException(String message) {
        super(message);
    }
}

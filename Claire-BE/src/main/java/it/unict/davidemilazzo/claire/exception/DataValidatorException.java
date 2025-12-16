package it.unict.davidemilazzo.claire.exception;

import java.util.HashMap;

public class DataValidatorException extends RuntimeException {
    private HashMap<String, String> properties;
    public DataValidatorException(String message, HashMap<String, String> properties) {
        super(message);
        this.properties = properties;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }
}

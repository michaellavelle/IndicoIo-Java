package io.indico.api.exception;

public class IndicoException extends Exception {

    public IndicoException(String message) {
        super(message);
    }

    public IndicoException(String message, Throwable cause) {
        super(message, cause);
    }
}

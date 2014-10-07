package io.indico.api.exception;

public class ConnectionException extends IndicoException {

    private static final String EXCEPTION_MESSAGE = "Connection error. Please verify your internet connection.";

    public ConnectionException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }
}
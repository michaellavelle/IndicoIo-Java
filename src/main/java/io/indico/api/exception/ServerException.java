package io.indico.api.exception;

public class ServerException extends IndicoException {

    private static final String EXCEPTION_MESSAGE_PATTERN = "Server error. Status code: %d";
    private static final String EXCEPTION_MESSAGE = "Server error.";

    public ServerException(int status, Throwable cause) {
        super(String.format(EXCEPTION_MESSAGE_PATTERN, status), cause);
    }

    public ServerException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }
}

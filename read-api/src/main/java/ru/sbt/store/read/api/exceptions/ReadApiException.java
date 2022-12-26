package ru.sbt.store.read.api.exceptions;

public class ReadApiException extends RuntimeException {

    protected String message;

    public ReadApiException() {
        super();
    }

    public ReadApiException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public ReadApiException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

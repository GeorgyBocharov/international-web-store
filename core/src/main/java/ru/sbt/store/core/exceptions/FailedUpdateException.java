package ru.sbt.store.core.exceptions;

public class FailedUpdateException extends OnlineStoreException {

    private static String messageTemplate = "Failed to update because of null id. Entity = ";

    public FailedUpdateException(String message) {
        super(messageTemplate + message);
    }
}

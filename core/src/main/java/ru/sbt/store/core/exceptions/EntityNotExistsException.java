package ru.sbt.store.core.exceptions;

public class EntityNotExistsException extends OnlineStoreException {

    private String message;

    public EntityNotExistsException(Long id, String className) {
        super();
        message = String.format("%s with id %d not exists", className, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

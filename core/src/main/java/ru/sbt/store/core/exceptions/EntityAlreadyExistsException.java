package ru.sbt.store.core.exceptions;

public class EntityAlreadyExistsException extends OnlineStoreException {

    private String message;

    public EntityAlreadyExistsException(String entity, String ownerEntity, Long id) {
        super();
        this.message = String.format("%s must be unique for %s with id %d", entity, ownerEntity, id);
    }

    public EntityAlreadyExistsException(String entity, Long id) {
        super();
        this.message = String.format("%s with id %d already exists, can't post new", entity, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

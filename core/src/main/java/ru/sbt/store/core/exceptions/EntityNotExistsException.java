package ru.sbt.store.core.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntityNotExistsException extends OnlineStoreException {

    private final String message;

    public EntityNotExistsException(Long id, String className) {
        super();
        message = String.format("%s with id %d not exists", className, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

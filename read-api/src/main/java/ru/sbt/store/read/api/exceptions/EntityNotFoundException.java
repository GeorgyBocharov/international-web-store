package ru.sbt.store.read.api.exceptions;

public class EntityNotFoundException extends ReadApiException {

    private static final String messageTemplate = "Entity %s wasn't found by id %s";

    public EntityNotFoundException(String entity, String id) {
        message = String.format(messageTemplate, entity, id);
    }
}

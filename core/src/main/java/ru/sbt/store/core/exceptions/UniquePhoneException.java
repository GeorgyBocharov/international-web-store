package ru.sbt.store.core.exceptions;

public class UniquePhoneException extends OnlineStoreException {
    private String message;

    public UniquePhoneException(String encryptedPhone) {
        super();
        this.message = String.format("Client with phone %s already exists", encryptedPhone);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

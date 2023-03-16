package com.springboot.rest.exception;


@SuppressWarnings("serial")
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(StorageException.generateMessage(message));
    }

    public StorageException(Throwable cause) {
        super(StorageException.generateMessage(cause.getMessage()));
    }

    public StorageException(String message, Throwable cause) {
        super(StorageException.generateMessage(message, cause.getMessage()));
    }

    private static String generateMessage(String message) {
        return "Could not initialize storage " + message;
    }

    private static String generateMessage(String message, String causeMessage) {
        return message + ": " + causeMessage;
    }
}
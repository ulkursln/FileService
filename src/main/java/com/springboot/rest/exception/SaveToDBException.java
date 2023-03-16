package com.springboot.rest.exception;

@SuppressWarnings("serial")
public class SaveToDBException extends RuntimeException {

    public SaveToDBException() {
        super(SaveToDBException.generateMessage());
    }

    private static String generateMessage() {
        return "Failed to save item(s) to DB. ";

    }
}

package com.springboot.rest.exception;

@SuppressWarnings("serial")
public class DeleteFromDBException extends RuntimeException {

    public DeleteFromDBException() {
        super(DeleteFromDBException.generateMessage());
    }

    private static String generateMessage() {
        return "Failed to delete item(s) from DB. ";
    }
}
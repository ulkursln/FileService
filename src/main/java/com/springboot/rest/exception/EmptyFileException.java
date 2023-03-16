package com.springboot.rest.exception;

@SuppressWarnings("serial")
public class EmptyFileException extends RuntimeException {

    public EmptyFileException() {
        super(EmptyFileException.generateMessage());
    }

    private static String generateMessage() {
        return "File is empty.";

    }
}
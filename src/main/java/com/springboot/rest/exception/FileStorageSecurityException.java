package com.springboot.rest.exception;

@SuppressWarnings("serial")
public class FileStorageSecurityException extends RuntimeException {

    public FileStorageSecurityException() {
        super(FileStorageSecurityException.generateMessage());
    }

    private static String generateMessage() {
        return "Cannot store file outside current directory.";
    }
}

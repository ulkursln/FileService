package com.springboot.rest.exception;

@SuppressWarnings("serial")
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException() {
        super(FileNotFoundException.generateMessage());
    }

    public FileNotFoundException(String filePath) {
        super(FileNotFoundException.generateMessage(filePath));
    }

    private static String generateMessage() {
        return "File not found.";

    }

    private static String generateMessage(String filePath) {
        return filePath + " not found.";

    }
}
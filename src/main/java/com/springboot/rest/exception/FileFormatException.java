package com.springboot.rest.exception;


import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class FileFormatException extends RuntimeException {

    public FileFormatException() {
        super(FileFormatException.generateMessage("Given file"));
    }

    public FileFormatException(String fileName) {
        super(FileFormatException.generateMessage(fileName));
    }

    private static String generateMessage(String fileName) {
        return StringUtils.capitalize(fileName) + " is not a text file ";

    }
}

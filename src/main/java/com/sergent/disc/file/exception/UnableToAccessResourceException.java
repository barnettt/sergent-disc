package com.sergent.disc.file.exception;

public class UnableToAccessResourceException extends RuntimeException {

    private static final String MESSAGE = "Unable to load files from resource directory";
    public UnableToAccessResourceException(){
        super(MESSAGE);
    }

    public UnableToAccessResourceException(String message){
        super(message);
    }
}

package com.elena.schoolMotivatorAppServer.controllers.utils.exception;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String message) {
        super(message);
    }
}

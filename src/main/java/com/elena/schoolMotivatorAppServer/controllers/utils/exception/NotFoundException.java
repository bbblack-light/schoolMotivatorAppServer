package com.elena.schoolMotivatorAppServer.controllers.utils.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}

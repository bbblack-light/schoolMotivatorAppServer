package com.elena.schoolMotivatorAppServer.controllers.utils.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}

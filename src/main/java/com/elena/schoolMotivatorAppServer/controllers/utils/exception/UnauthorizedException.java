package com.elena.schoolMotivatorAppServer.controllers.utils.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}

package com.elena.schoolMotivatorAppServer.controllers.utils.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}

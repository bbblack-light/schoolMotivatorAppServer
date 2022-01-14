package com.elena.schoolMotivatorAppServer.controllers.utils.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OperationResponse {
    private String message;

    public OperationResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package com.elena.schoolMotivatorAppServer.controllers.utils.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OperationObjectResponse extends OperationResponse {

    private Object object;

    public OperationObjectResponse(String message) {
        super(message);
    }
}

package com.elena.schoolMotivatorAppServer.controllers.utils.response;

import com.elena.schoolMotivatorAppServer.model.session.SessionItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionResponse extends OperationResponse {
    public SessionItem getItem() {
        return item;
    }

    public void setItem(SessionItem item) {
        this.item = item;
    }

    @ApiModelProperty(required = true, value = "")
    private SessionItem item;

    public SessionResponse(String message) {
        super(message);
    }
}

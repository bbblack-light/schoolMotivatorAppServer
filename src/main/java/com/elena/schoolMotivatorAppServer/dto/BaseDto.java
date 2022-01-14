package com.elena.schoolMotivatorAppServer.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

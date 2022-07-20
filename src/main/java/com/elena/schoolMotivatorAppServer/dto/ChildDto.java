package com.elena.schoolMotivatorAppServer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ChildDto extends BaseDto {
    @JsonProperty("edId")
    private Long EDId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private ClassDto actualClass;
    private String parentId;
    private String password;
}

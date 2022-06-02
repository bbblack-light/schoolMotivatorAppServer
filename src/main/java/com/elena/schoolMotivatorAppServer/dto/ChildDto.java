package com.elena.schoolMotivatorAppServer.dto;

import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class ChildDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private ClassDto actualClass;
    private String parentId;
    private String password;
}

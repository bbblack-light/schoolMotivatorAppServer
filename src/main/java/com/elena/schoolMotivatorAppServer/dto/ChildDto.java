package com.elena.schoolMotivatorAppServer.dto;

import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class ChildDto extends BaseDto {
    private String name;
    private Date birthday;
    private ClassDto actualClass;
    private UserDto parent;
    private String password;
}

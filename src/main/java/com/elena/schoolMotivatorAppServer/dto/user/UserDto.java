package com.elena.schoolMotivatorAppServer.dto.user;

import com.elena.schoolMotivatorAppServer.dto.ChildDto;
import com.elena.schoolMotivatorAppServer.model.user.Role;
import com.elena.schoolMotivatorAppServer.model.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private String userId;
    private String password = "";
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private List<ChildDto> children;
    private Role role;

    public static UserDto convertFromEntity(User user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto, "password");
        return dto;
    }

    public static List<UserDto> convertFromEntities(List<User> users) {
        return users.stream().map(UserDto::convertFromEntity).collect(Collectors.toList());
    }
}

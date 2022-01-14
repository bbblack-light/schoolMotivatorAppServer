package com.elena.schoolMotivatorAppServer.dto.user;

import com.elena.schoolMotivatorAppServer.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDataDto {
    private String userId;
    private String password;
    private String email;

    public static UserDetailDto convertFromEntity(User user) {
        UserDetailDto dto = new UserDetailDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static List<UserDetailDto> convertFromEntities(List<User> users) {
        return users.stream().map(user -> UserDetailDto.convertFromEntity(user)).collect(Collectors.toList());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

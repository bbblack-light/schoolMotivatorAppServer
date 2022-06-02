package com.elena.schoolMotivatorAppServer.controllers;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import com.elena.schoolMotivatorAppServer.services.AchievementService;
import com.elena.schoolMotivatorAppServer.services.EmailService;
import com.elena.schoolMotivatorAppServer.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Authentication"})
public class UserController {
    private final AchievementService achievementService;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public UserController(AchievementService achievementService, EmailService emailService, UserService userService) {
        this.achievementService = achievementService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public UserDto getUserInformation(@PathVariable(value = "name", required = false) String userIdParam) {
        return userService.getUserInformation(userIdParam);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/registration")
    public OperationResponse addNewUser(@RequestBody UserDto user) {
        String password = user.getPassword();
        boolean userAddSuccess = userService.addNewUser(user);
        if (userAddSuccess && user.getEmail()!=null && !user.getEmail().isEmpty()) {

            OperationResponse emailResponse = this.emailService.sendSimpleMessage(user.getEmail(),
                "Регистрация в мобильном приложении \"Школьный мотиватор для детей и родителей\"",
                "Ваш профиль был зарегестрирован! " +
                "Ваш логин: " + user.getUserId() + " " +
                "Ваш пароль:" + password);

            if (emailResponse.getMessage().contains("ok")) {
                return new OperationResponse("ok");
            }
            else {
                userService.delete(user.getUserId());
                return emailResponse;
            }
        }
        else {
            userService.delete(user.getUserId());
            return new OperationResponse("Произошла непредвиденная ошибка");
        }
    }

    @PostMapping("/edit/user")
    public OperationResponse editUser(@RequestBody UserDto user) {
        boolean userEditSuccess = userService.edit(user);
        return userEditSuccess ? new OperationResponse("User Edited") : new OperationResponse("Unable to edit user");
    }

    @GetMapping("/user/info/{userId}")
    public UserDto getUserInfo(@PathVariable("userId") String userId) {
        return userService.getUserInformation(userId);
    }

    @DeleteMapping("/user/info/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId) {
        return userService.delete(userId);
    }
}

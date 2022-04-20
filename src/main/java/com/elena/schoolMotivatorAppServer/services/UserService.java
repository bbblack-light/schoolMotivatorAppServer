package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import com.elena.schoolMotivatorAppServer.model.user.Role;
import com.elena.schoolMotivatorAppServer.model.user.User;
import com.elena.schoolMotivatorAppServer.repo.UserRepo;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;


    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto getUserInformation(String userIdParam) {
        String loggedInUserId = getLoggedInUserId();
        User user;
        if (Strings.isNullOrEmpty(userIdParam)) {
            user = getLoggedInUser();
        } else if (loggedInUserId.equals(userIdParam)) {
            user = getLoggedInUser();
        } else {
            user = getUserInfoByUserId(userIdParam);
        }
        return UserDto.convertFromEntity(user);
    }

    public String getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "nosession";
        }
        return auth.getName();
    }


    public User getLoggedInUser() {
        String loggedInUserId = this.getLoggedInUserId();
        return this.getUserInfoByUserId(loggedInUserId);
    }

    public User getUserInfoByUserId(String userId) {
        return this.userRepo.findOneByUserId(userId).orElse(null);
    }


    public boolean insertOrSaveUser(User user) {
        this.userRepo.save(user);
        return true;
    }

    public boolean addNewUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User newUser = this.getUserInfoByUserId(user.getUserId());
        if (newUser == null) {
            user.setRole(Role.USER);
            return this.insertOrSaveUser(user);
        } else {
            return false;
        }
    }

    public boolean edit(UserDto user) {
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        User buffUser = this.getUserInfoByUserId(user.getUserId());
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            buffUser.setPassword(user.getPassword());
        }
        buffUser.setEmail(user.getEmail());
        buffUser.setFirstName(user.getFirstName());
        buffUser.setLastName(user.getLastName());
        return this.insertOrSaveUser(buffUser);
    }

    public List<UserDto> findAll() {
        return UserDto.convertFromEntities(userRepo.findAll());
    }

    public ResponseEntity<Object> delete(String userId) {
        Optional<User> user = userRepo.findOneByUserId(userId);
        if (user.isPresent()) {
           userRepo.delete(user.get());
            return ResponseEntity.ok().build();
        }
        throw new NotFoundException("Не найден пользователь");
    }
}


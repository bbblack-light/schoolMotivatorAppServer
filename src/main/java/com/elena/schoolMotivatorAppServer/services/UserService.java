package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import com.elena.schoolMotivatorAppServer.model.user.Role;
import com.elena.schoolMotivatorAppServer.model.user.User;
import com.elena.schoolMotivatorAppServer.repo.UserRepo;
import com.google.common.base.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
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
        if (user == null) {
            user =  new User(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    Role.USER);
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public String getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "nosession";
        }
        return auth.getName();
    }


    @Transactional
    public User getLoggedInUser() {
        String loggedInUserId = this.getLoggedInUserId();
        return this.getUserInfoByUserId(loggedInUserId);
    }

    public User getUserInfoByUserId(String userId) {
        return this.userRepo.findOneByUserId(userId).orElse(null);
    }


    @Transactional
    public boolean insertOrSaveUser(User user) {
        this.userRepo.save(user);
        return true;
    }

    @Transactional
    public boolean addNewUser(UserDto dto) {
        User user = modelMapper.map(dto, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User newUser = this.getUserInfoByUserId(user.getUserId());
        if (newUser == null) {
            user.setRole(Role.USER);
            return this.insertOrSaveUser(user);
        } else {
            return false;
        }
    }

    @Transactional
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

    @Transactional
    public List<UserDto> findAll() {
        return UserDto.convertFromEntities(userRepo.findAll());
    }

    @Transactional
    public ResponseEntity<Object> delete(String userId) {
        Optional<User> user = userRepo.findOneByUserId(userId);
        if (user.isPresent()) {
           userRepo.delete(user.get());
            return ResponseEntity.ok().build();
        }
        throw new NotFoundException("Не найден пользователь");
    }
}


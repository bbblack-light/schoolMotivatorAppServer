package com.elena.schoolMotivatorAppServer;

import com.elena.schoolMotivatorAppServer.model.user.Role;
import com.elena.schoolMotivatorAppServer.model.user.User;
import com.elena.schoolMotivatorAppServer.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    private static UserService service;

    public Initializer(UserService service) {
        this.service = service;
    }

    public static void createAdmin() {
        User user = new User();
        user.setPassword("$2a$10$U0fbYjlwLFraz1UHrjjX2.rErJoP/X.KIRthU9jtmMkRACYBeVOpG");
        user.setUserId("admin");
        user.setRole(Role.ADMIN);
        user.setFirstName("Админ");
        user.setLastName("");
        service.insertOrSaveUser(user);
    }

    public static void createParent() {
        User user = new User();
        user.setPassword("$2a$10$U0fbYjlwLFraz1UHrjjX2.rErJoP/X.KIRthU9jtmMkRACYBeVOpG");
        user.setUserId("parent1");
        user.setRole(Role.PARENT);
        user.setFirstName("Евгений");
        user.setLastName("Королев");
        service.insertOrSaveUser(user);

    }

    public static void createChild() {
        User user = new User();
        user.setPassword("$2a$10$U0fbYjlwLFraz1UHrjjX2.rErJoP/X.KIRthU9jtmMkRACYBeVOpG");
        user.setUserId("child1");
        user.setRole(Role.CHILD);
        user.setFirstName("Иванов");
        user.setLastName("Иван");
        service.insertOrSaveUser(user);

    }

}

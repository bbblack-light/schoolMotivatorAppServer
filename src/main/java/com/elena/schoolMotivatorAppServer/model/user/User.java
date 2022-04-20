package com.elena.schoolMotivatorAppServer.model.user;

import com.elena.schoolMotivatorAppServer.model.Child;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "m_User")
@Data
public class User {
    @Id
    private String userId;
    private String password = "";
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    @OneToMany(mappedBy = "parent")
    private List<Child> children;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        new User(
                "new",
                "new",
                "new",
                "new",
                "new",
                "",
                Role.USER);
    }

    public User(String userId,
                String password,
                String firstName,
                String lastName,
                String patronymic,
                String email,
                Role role) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.role = role;
    }

    public String getFullName() {
        return this.firstName + this.lastName + this.patronymic;
    }
}


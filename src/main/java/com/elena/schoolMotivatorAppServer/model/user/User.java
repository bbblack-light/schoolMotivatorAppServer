package com.elena.schoolMotivatorAppServer.model.user;

import javax.persistence.*;
import java.util.List;

@Entity(name = "m_User")
public class User {

    @Id
    private String userId;

    private String password = "";

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    @JoinColumn(columnDefinition = "parent_id")
    @ManyToOne
    private User parent;

    @OneToMany(mappedBy = "parent")
    private List<User> childs;

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
                Role.PARENT);
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return this.firstName + this.lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public List<User> getChilds() {
        return childs;
    }

    public void setChilds(List<User> childs) {
        this.childs = childs;
    }
}


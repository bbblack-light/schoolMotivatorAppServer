package com.elena.schoolMotivatorAppServer.model;

import com.elena.schoolMotivatorAppServer.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity(name = "child")
@Data
public class Child extends BaseEntity {
    @Column(name = "ed_id")
    private Long EDId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthday;
    private LocalDate maxDateLoadingFromED;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;
    private String base64;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes actualClass;
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChildAchievement> achievement;
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Goals> goals;
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Grades> grades;
    private String password;
}

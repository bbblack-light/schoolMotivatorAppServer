package com.elena.schoolMotivatorAppServer.model;

import com.elena.schoolMotivatorAppServer.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "child")
@Data
public class Child extends BaseEntity {
    private String name;
    private Date birthday;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes actualClass;
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private List<ChildAchievement> achievement;
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private List<Goals> goals;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private List<Grades> grades;
    private String password;
}

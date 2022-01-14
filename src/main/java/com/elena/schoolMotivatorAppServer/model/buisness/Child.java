package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity(name = "child")
@Setter
@Getter
public class Child extends BaseEntity {
    private String name;
    private Date birthday;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes actualClass;
    @OneToMany(mappedBy = "child")
    private List<ChildAchievement> achievement;
    @OneToMany(mappedBy = "child")
    private List<Goals> goals;
    @OneToMany(mappedBy = "child")
    private List<Grades> grades;
}

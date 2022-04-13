package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity(name = "child")
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

    public List<Grades> getGrades() {
        return grades;
    }

    public void setGrades(List<Grades> grades) {
        this.grades = grades;
    }

    @OneToMany(mappedBy = "child")
    private List<Grades> grades;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Classes getActualClass() {
        return actualClass;
    }

    public void setActualClass(Classes actualClass) {
        this.actualClass = actualClass;
    }

    public List<ChildAchievement> getAchievement() {
        return achievement;
    }

    public void setAchievement(List<ChildAchievement> achievement) {
        this.achievement = achievement;
    }

    public List<Goals> getGoals() {
        return goals;
    }

    public void setGoals(List<Goals> goals) {
        this.goals = goals;
    }
}

package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "grades")
public class Grades extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    private float grade;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}

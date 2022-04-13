package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "goals")
public class Goals extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    private Date dateStart;
    private Date dateEnd;
    private int countOfGrades;
    private boolean isFinished;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getCountOfGrades() {
        return countOfGrades;
    }

    public void setCountOfGrades(int countOfGrades) {
        this.countOfGrades = countOfGrades;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}

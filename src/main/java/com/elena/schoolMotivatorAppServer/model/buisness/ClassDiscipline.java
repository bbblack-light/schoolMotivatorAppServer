package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;

import javax.persistence.*;

@Entity(name = "join_class_discipline")
public class ClassDiscipline extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private Classes classes;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}

package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "join_class_discipline")
@Data
public class ClassDiscipline extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private Classes classes;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
}

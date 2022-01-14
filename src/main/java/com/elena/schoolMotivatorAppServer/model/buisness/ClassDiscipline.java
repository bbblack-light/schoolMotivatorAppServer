package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "join_class_discipline")
@Getter
@Setter
public class ClassDiscipline extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
}

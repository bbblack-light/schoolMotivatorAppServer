package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "goals")
@Data
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
}

package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity(name = "goals")
@Data
public class Goals extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int value;
    private int progress;
    private int countOfGrades;
    private boolean isFinished;
    private LocalDate firstDateFinished;
    private boolean isInAchievementProgress;
    private String reward;
}

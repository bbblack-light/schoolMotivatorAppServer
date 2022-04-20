package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "grades")
@Data
public class Grades extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    private float grade;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
}

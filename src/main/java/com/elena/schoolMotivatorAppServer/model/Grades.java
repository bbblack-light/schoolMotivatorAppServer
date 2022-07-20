package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "grades")
@Data
public class Grades extends BaseEntity {
    @Column(name = "ed_id")
    private Long EDId;
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    private int value;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
}

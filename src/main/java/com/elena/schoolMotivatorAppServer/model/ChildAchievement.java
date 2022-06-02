package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity(name = "join_child_achievement")
@Data
public class ChildAchievement extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    private int progress;
    private LocalDate date;
    private boolean isFinished;
}

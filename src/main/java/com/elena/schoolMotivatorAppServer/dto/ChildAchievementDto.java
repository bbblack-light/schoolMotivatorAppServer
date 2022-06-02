package com.elena.schoolMotivatorAppServer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChildAchievementDto {
    private Long childId;
    private AchievementDto achievement;
    private int progress;
    private Date date;
    private boolean isFinished;
}

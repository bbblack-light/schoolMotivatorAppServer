package com.elena.schoolMotivatorAppServer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoalsDto extends BaseDto {
    private String name;
    private Long childId;
    private DisciplineDto discipline;
    private Date dateStart;
    private Date dateEnd;
    private int countOfGrades;
    private int value;
    private int progress;
    private boolean isFinished;
    private String reward;
}

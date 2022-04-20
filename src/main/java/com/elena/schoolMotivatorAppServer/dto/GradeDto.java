package com.elena.schoolMotivatorAppServer.dto;
import lombok.Data;

import java.util.Date;

@Data
public class GradeDto extends BaseDto {
    private DisciplineDto discipline;
    private Long childId;
    private float grade;
    private Date date;
}

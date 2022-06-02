package com.elena.schoolMotivatorAppServer.dto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class GradeDto extends BaseDto {
    private DisciplineDto discipline;
    private Long childId;
    private int value;
    private Date date;
}

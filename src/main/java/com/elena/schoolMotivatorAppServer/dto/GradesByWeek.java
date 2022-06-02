package com.elena.schoolMotivatorAppServer.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GradesByWeek {
    private Date start;
    private Date end;
    private List<GradeDto> grades;
}

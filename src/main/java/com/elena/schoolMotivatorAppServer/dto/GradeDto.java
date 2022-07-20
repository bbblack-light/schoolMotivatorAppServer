package com.elena.schoolMotivatorAppServer.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class GradeDto extends BaseDto {
    private DisciplineDto discipline;
    @JsonProperty("edId")
    private Long EDId;
    private Long childId;
    private int value;
    private Date date;
}

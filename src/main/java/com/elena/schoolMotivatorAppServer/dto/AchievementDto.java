package com.elena.schoolMotivatorAppServer.dto;

import com.elena.schoolMotivatorAppServer.model.AchievementType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AchievementDto extends BaseDto {
    private String name;
    private String description;
    private String base64;
    private int childrenCount;
    private AchievementType type;
    private Integer countOfGrades;
    private Integer gradesValues;
    private DisciplineDto discipline;
    private Integer countOfGoals;
}

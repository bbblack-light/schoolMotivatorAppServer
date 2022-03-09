package com.elena.schoolMotivatorAppServer.dto.buisness;

import com.elena.schoolMotivatorAppServer.dto.BaseDto;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ClassDto extends BaseDto {
    private String name;
    private String description;
    private List<DisciplineDto> disciplines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DisciplineDto> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<DisciplineDto> disciplines) {
        this.disciplines = disciplines;
    }
}

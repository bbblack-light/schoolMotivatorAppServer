package com.elena.schoolMotivatorAppServer.dto.buisness;

import com.elena.schoolMotivatorAppServer.dto.BaseDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DisciplineDto extends BaseDto {
    private String name;
    private String description;

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
}

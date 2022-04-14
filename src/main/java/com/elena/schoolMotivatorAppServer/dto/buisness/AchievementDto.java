package com.elena.schoolMotivatorAppServer.dto.buisness;

import com.elena.schoolMotivatorAppServer.dto.BaseDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AchievementDto extends BaseDto {
    private String name;
    private String description;
    private String base64;
    private int childrenCount;

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

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}

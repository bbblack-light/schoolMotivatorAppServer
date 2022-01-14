package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "achievement")
@Setter
@Getter
public class Achievement extends BaseEntity {
    private String name;
    private String description;
    private String base64;
    @OneToMany(mappedBy = "achievement")
    private List<ChildAchievement> children;
}

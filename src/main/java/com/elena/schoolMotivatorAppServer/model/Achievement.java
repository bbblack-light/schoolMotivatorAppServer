package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity(name = "achievement")
@Data
public class Achievement extends BaseEntity {
    private String name;
    private String description;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String base64;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achievement")
    private List<ChildAchievement> children;

    private AchievementType type;

    private Integer countOfGrades;
    private Integer gradesValues;
    @ManyToOne
    @JoinColumn
    private Discipline discipline;

    private Integer countOfGoals;
}

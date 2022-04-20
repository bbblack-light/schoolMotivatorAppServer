package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
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
}

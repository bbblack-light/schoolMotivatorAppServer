package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "classes")
@Data
public class Classes extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="classes")
    private List<ClassDiscipline> disciplines;
}

package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "classes")
@Data
public class Classes extends BaseEntity {
    @Column(name = "ed_id", unique = true)
    private Long EDId;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="classes")
    private List<ClassDiscipline> disciplines;
}

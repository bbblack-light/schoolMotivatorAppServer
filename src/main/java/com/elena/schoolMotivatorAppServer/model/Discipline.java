package com.elena.schoolMotivatorAppServer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "discipline")
@Data
public class Discipline extends BaseEntity {
    private String name;
    @Lob
    @Basic(fetch= FetchType.LAZY, optional=true)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="discipline")
    private List<ClassDiscipline> classes;
}

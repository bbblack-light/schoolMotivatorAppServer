package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "discipline")
public class Discipline extends BaseEntity {
    private String name;
    @Lob
    @Basic(fetch= FetchType.LAZY, optional=true)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="discipline")
    private List<ClassDiscipline> classes;

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

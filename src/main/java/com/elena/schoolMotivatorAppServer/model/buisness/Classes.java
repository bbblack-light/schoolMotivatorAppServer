package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "classes")
public class Classes extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="classes")
    private List<ClassDiscipline> disciplines;

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

    public List<ClassDiscipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<ClassDiscipline> disciplines) {
        this.disciplines = disciplines;
    }
    //todo: check for disciplines
}

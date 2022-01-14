package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "classes")
@Getter
@Setter
public class Classes extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(mappedBy = "discipline")
    private List<ClassDiscipline> disciplines;
    //todo: check for disciplines
}

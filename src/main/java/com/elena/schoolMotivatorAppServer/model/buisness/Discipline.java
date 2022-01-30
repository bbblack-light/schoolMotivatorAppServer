package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "discipline")
@Getter
@Setter
public class Discipline extends BaseEntity {
    private String name;
    private String description;
}

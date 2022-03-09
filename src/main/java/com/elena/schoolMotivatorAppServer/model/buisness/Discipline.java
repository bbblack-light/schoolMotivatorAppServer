package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Entity(name = "discipline")
@Getter
@Setter
public class Discipline extends BaseEntity {
    private String name;
    @Lob
    @Basic(fetch= FetchType.LAZY, optional=true)
    private String description;
}

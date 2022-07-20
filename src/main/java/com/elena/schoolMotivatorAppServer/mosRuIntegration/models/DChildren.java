package com.elena.schoolMotivatorAppServer.mosRuIntegration.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DChildren {
    private long id;
    private String last_name;
    private String first_name;
    private String middle_name;
    private Date birth_date;
    private String class_name;
    private Long class_unit_id;
    private List<DGroups> groups;
}

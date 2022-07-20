package com.elena.schoolMotivatorAppServer.mosRuIntegration.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Lesson {
    private Long subject_id;
    private String subject_name;
    private List<Mark> marks;
}

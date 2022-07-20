package com.elena.schoolMotivatorAppServer.mosRuIntegration.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DGroups {
    private Long id;
    private String name;
    private boolean is_fake;
    private Long subject_id;
}

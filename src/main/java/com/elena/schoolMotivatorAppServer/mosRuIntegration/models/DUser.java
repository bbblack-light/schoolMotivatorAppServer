package com.elena.schoolMotivatorAppServer.mosRuIntegration.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DUser {
    List<DChildren> children;
}

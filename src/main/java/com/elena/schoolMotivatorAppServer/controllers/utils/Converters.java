package com.elena.schoolMotivatorAppServer.controllers.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Converters {
    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        if (dateToConvert==null) return null;
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        if (dateToConvert==null) return null;
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static boolean between(LocalDate medium, LocalDate start, LocalDate end) {
        LocalDate s = LocalDate.of(start.getYear(), start.getMonth(), start.getDayOfMonth());
        LocalDate e = LocalDate.of(end.getYear(), end.getMonth(), end.getDayOfMonth());
        return medium.isAfter(s) && medium.isBefore(e) || medium.isEqual(s) || medium.isEqual(e);
    }
}

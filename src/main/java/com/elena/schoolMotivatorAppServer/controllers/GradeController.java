package com.elena.schoolMotivatorAppServer.controllers;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GradeDto;
import com.elena.schoolMotivatorAppServer.dto.GradesByWeek;
import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import com.elena.schoolMotivatorAppServer.services.EDService;
import com.elena.schoolMotivatorAppServer.services.GradeService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@Api(tags = {"Grades"})
@RequestMapping("/grade")
public class GradeController {
    private final GradeService gradeService;
    private final EDService edService;

    @Autowired
    public GradeController(GradeService gradeService, EDService edService) {
        this.gradeService = gradeService;
        this.edService = edService;
    }

    @PostMapping("/")
    public GradeDto update(@RequestBody GradeDto dto) throws ParseException {
        return gradeService.update(dto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return gradeService.delete(id);
    }

    @GetMapping("/allByChild/{childId}")
    public List<GradeDto> getAllByChildId(@PathVariable("childId") Long childId) {
        return gradeService.getAllByChildId(childId);
    }

    @GetMapping("/{id}")
    public GradeDto getById(@PathVariable("id") Long id) {
        return gradeService.getById(id);
    }

    @GetMapping("/byPeriod/{childId}")
    public GradesByWeek getByPeriodByChildId(@PathVariable("childId") Long childId, @RequestBody GradesByWeek gradesByWeek) throws IOException {
        return gradeService.getByPeriod(childId, gradesByWeek);
    }

    @GetMapping("/last_week/{childId}")
    public List<GradeDto> getLastWeekByChildId(@PathVariable("childId") Long childId) {
        return gradeService.getLastWeekByChildId(childId);
    }

    @SneakyThrows
    @GetMapping("/today_grades/{childId}")
    public List<GradeDto> getTodayByChildId(@PathVariable("childId") Long childId) throws ParseException, IOException {
        return gradeService.getTodayByChildId(childId);
    }

    @GetMapping("/ed/updateByPeriod/{childId}")
    public OperationResponse addEDToken(@PathVariable("childId") Long childId, @RequestBody GradesByWeek dates) throws IOException {
        return edService.updateGradesByED(childId, dates);
    }
}

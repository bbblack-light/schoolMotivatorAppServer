package com.elena.schoolMotivatorAppServer.controllers;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GradeDto;
import com.elena.schoolMotivatorAppServer.services.GradeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Grades"})
@RequestMapping("/grade")
public class GradeController {
    private GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/")
    public GradeDto update(@RequestBody GradeDto dto) {
        return gradeService.update(dto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return gradeService.delete(id);
    }

    @GetMapping("/allBuChild/{childId}")
    public List<GradeDto> getAllByChildId(@PathVariable("childId") Long childId) {
        return gradeService.getAllByChildId(childId);
    }

    @GetMapping("/{id}")
    public GradeDto getById(@PathVariable("id") Long id) {
        return gradeService.getById(id);
    }
}

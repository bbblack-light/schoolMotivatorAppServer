package com.elena.schoolMotivatorAppServer.controllers;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GoalsDto;
import com.elena.schoolMotivatorAppServer.services.GoalsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@Api(tags = {"Goals"})
@RequestMapping("/goals")
public class GoalsController {

    private final GoalsService goalsService;

    @Autowired
    public GoalsController(GoalsService goalsService) {

        this.goalsService = goalsService;
    }

    @PostMapping("/")
    public GoalsDto update(@RequestBody GoalsDto dto) throws ParseException {
        return goalsService.update(dto, false);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return goalsService.delete(id);
    }

    @GetMapping("/allByChild/{childId}")
    public List<GoalsDto> getAllByChildId(@PathVariable("childId") Long childId) {
        return goalsService.getAllByChild(childId);
    }

    @GetMapping("/{id}")
    public GoalsDto getById(@PathVariable("id") Long id) {
        return goalsService.getById(id);
    }

    @GetMapping("/in_progress/{childId}")
    public List<GoalsDto> getAllInProgress(@PathVariable("childId") Long childId) {
        return goalsService.getAllInProgress(childId);
    }

    @GetMapping("/finished/{childId}")
    public List<GoalsDto> getAllFinished(@PathVariable("childId") Long childId) throws ParseException {
        return goalsService.getAllFinished(childId);
    }
}

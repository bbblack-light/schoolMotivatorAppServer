package com.elena.schoolMotivatorAppServer.controllers;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.AchievementDto;
import com.elena.schoolMotivatorAppServer.services.AchievementService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Achievement"})
@RequestMapping("/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService disciplineService) {
        this.achievementService = disciplineService;
    }

    @PostMapping("/")
    public AchievementDto update(@RequestBody AchievementDto dto) {
        return achievementService.update(dto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return achievementService.delete(id);
    }

    @GetMapping("/all")
    public List<AchievementDto> getAll() {
        return achievementService.getAll();
    }

    @GetMapping("/{id}")
    public AchievementDto getById(@PathVariable("id") Long id) {
        return achievementService.getById(id);
    }
}

package com.elena.schoolMotivatorAppServer.controllers;


import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.ClassDto;
import com.elena.schoolMotivatorAppServer.services.ClassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Classes"})
@RequestMapping("/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/")
    public ClassDto update(@RequestBody ClassDto dto) {
        return classService.update(dto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return classService.delete(id);
    }

    @GetMapping("/all")
    public List<ClassDto> getAll() {
        return classService.getAll();
    }

    @GetMapping("/{id}")
    public ClassDto getById(@PathVariable("id") Long id) {
        return classService.getById(id);
    }
}

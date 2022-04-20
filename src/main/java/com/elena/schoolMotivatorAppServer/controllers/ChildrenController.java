package com.elena.schoolMotivatorAppServer.controllers;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.ChildDto;
import com.elena.schoolMotivatorAppServer.services.ChildrenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Children"})
@RequestMapping("/children")
public class ChildrenController {
    private final ChildrenService childrenService;

    @Autowired
    public ChildrenController(ChildrenService disciplineService) {
        this.childrenService = disciplineService;
    }

    @PostMapping("/")
    public ChildDto update(@RequestBody ChildDto dto) {
        return childrenService.update(dto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return childrenService.delete(id);
    }

    @GetMapping("/all")
    public List<ChildDto> getAll() {
        return childrenService.getAll();
    }

    @GetMapping("/{id}")
    public ChildDto getById(@PathVariable("id") Long id) {
        return childrenService.getById(id);
    }

    @GetMapping("/allByUser/{userId}")
    public List<ChildDto> getByUserId(@PathVariable("userId") String userId) {
        return childrenService.getAllByUser(userId);
    }
}

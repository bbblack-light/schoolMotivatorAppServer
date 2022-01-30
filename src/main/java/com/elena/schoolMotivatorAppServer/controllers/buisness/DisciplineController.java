package com.elena.schoolMotivatorAppServer.controllers.buisness;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.buisness.DisciplineDto;
import com.elena.schoolMotivatorAppServer.services.DisciplineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Discipline"})
@RequestMapping("/discipline")
public class DisciplineController {
    private final DisciplineService disciplineService;

    @Autowired
    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping("/")
    public DisciplineDto update(@RequestBody DisciplineDto dto) {
        return disciplineService.update(dto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse delete(@PathVariable("id") Long id) {
        return disciplineService.delete(id);
    }

    @GetMapping("/all")
    public List<DisciplineDto> getAll(Pageable pageable) {
        return disciplineService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public DisciplineDto getById(@PathVariable("id") Long id) {
        return disciplineService.getById(id);
    }
}

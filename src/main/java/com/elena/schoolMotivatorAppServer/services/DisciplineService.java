package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.buisness.DisciplineDto;
import com.elena.schoolMotivatorAppServer.model.buisness.Discipline;
import com.elena.schoolMotivatorAppServer.repo.DisciplinesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DisciplineService {
    private final DisciplinesRepo disciplinesRepo;

    @Autowired
    public DisciplineService(DisciplinesRepo disciplinesRepo) {
        this.disciplinesRepo = disciplinesRepo;
    }

    public DisciplineDto update(DisciplineDto dto) {
        return (DisciplineDto)disciplinesRepo.save((Discipline)dto);
    }

    public OperationResponse delete(long id) {
        if (!disciplinesRepo.existsById(id)) {
            return new OperationResponse(" Дисциплина не существует");
        }
        Discipline discipline = disciplinesRepo.getById(id);
        disciplinesRepo.delete(discipline);
        return new OperationResponse("Дисциплина удалена");
    }

    public DisciplineDto getById(long id) {
        if (!disciplinesRepo.existsById(id)) {
            throw new NotFoundException(" Дисциплина не существует");
        }
        return (DisciplineDto)disciplinesRepo.getById(id);
    }

    public List<DisciplineDto> getAll(Pageable pageable) {
        return disciplinesRepo.findAll(pageable).get()
                .map(x -> (DisciplineDto)x)
                .collect(Collectors.toList());
    }
}

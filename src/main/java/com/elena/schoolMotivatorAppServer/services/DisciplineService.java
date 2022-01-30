package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.buisness.DisciplineDto;
import com.elena.schoolMotivatorAppServer.model.buisness.Discipline;
import com.elena.schoolMotivatorAppServer.repo.DisciplinesRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DisciplineService {
    private final DisciplinesRepo disciplinesRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public DisciplineService(DisciplinesRepo disciplinesRepo, ModelMapper modelMapper) {
        this.disciplinesRepo = disciplinesRepo;
        this.modelMapper = modelMapper;
    }

    public DisciplineDto update(DisciplineDto dto) {
        Discipline d = modelMapper.map(dto, Discipline.class);
        return modelMapper.map(disciplinesRepo.save(d), DisciplineDto.class);
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
        return modelMapper.map(disciplinesRepo.getById(id), DisciplineDto.class);
    }

    public List<DisciplineDto> getAll(Pageable pageable) {
        return disciplinesRepo.findAll(pageable).get()
                .map(x -> modelMapper.map(x, DisciplineDto.class))
                .collect(Collectors.toList());
    }
}

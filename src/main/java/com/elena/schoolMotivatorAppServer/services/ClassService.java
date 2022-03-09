package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.buisness.ClassDto;
import com.elena.schoolMotivatorAppServer.model.buisness.Classes;
import com.elena.schoolMotivatorAppServer.repo.ClassesRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {
    private final ClassesRepo classesRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public ClassService(ClassesRepo classesRepo, ModelMapper modelMapper) {
        this.classesRepo = classesRepo;
        this.modelMapper = modelMapper;
    }

    //todo: not saving disciplines correctly
    public ClassDto update(ClassDto dto) {
        Classes classes = modelMapper.map(dto, Classes.class);
        classes.getDisciplines().stream().forEach(d -> d.setClasses(classes));
        return modelMapper.map(classesRepo.save(classes), ClassDto.class);
    }

    public OperationResponse delete(long id) {
        if (!classesRepo.existsById(id)) {
            return new OperationResponse("Класс не существует");
        }
        Classes classes = classesRepo.getById(id);
        classesRepo.delete(classes);
        return new OperationResponse("Класс удален");
    }

    public ClassDto getById(long id) {
        if (!classesRepo.existsById(id)) {
            throw new NotFoundException(" Дисциплина не существует");
        }
        return modelMapper.map(classesRepo.getById(id), ClassDto.class);
    }

    public List<ClassDto> getAll() {
        return classesRepo.findAll().stream()
                .map(x -> modelMapper.map(x, ClassDto.class))
                .collect(Collectors.toList());
    }
}

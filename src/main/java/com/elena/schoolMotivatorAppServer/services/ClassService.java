package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.ClassDto;
import com.elena.schoolMotivatorAppServer.dto.DisciplineDto;
import com.elena.schoolMotivatorAppServer.model.ClassDiscipline;
import com.elena.schoolMotivatorAppServer.model.Classes;
import com.elena.schoolMotivatorAppServer.repo.ClassesDisciplineRepo;
import com.elena.schoolMotivatorAppServer.repo.ClassesRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClassService {
    private final ClassesRepo classesRepo;
    private final ModelMapper modelMapper;
    private final ClassesDisciplineRepo classesDisciplineRepo;

    @Autowired
    public ClassService(ClassesRepo classesRepo, ModelMapper modelMapper, ClassesDisciplineRepo classesDisciplineRepo) {
        this.classesRepo = classesRepo;
        this.modelMapper = modelMapper;
        this.classesDisciplineRepo = classesDisciplineRepo;
    }

    @Transactional
    public ClassDto update(ClassDto dto) {
        Classes fromrepo;
        if (dto.getId() == null){
            Classes classes = modelMapper.map(dto, Classes.class);
            classes.setDisciplines(null);
            fromrepo = classesRepo.save(classes);
            fromrepo.setDisciplines(new ArrayList<>());
        }
        else {
            if (!classesRepo.existsById(dto.getId())) throw new NotFoundException("Класс не существует");
            fromrepo = classesRepo.getById(dto.getId());
        }


        if (CollectionUtils.isEmpty(dto.getDisciplines()))
            dto.setDisciplines(new ArrayList<>());
        if (CollectionUtils.isEmpty(fromrepo.getDisciplines()))
            fromrepo.setDisciplines(new ArrayList<>());

        List<ClassDiscipline> remove = new ArrayList<>();
        //remove elements if dto dont have it
        for (ClassDiscipline classDiscipline : fromrepo.getDisciplines()) {
            boolean any = dto.getDisciplines().stream()
                    .anyMatch(dtoDiscipline -> Objects.equals(dtoDiscipline.getId(), classDiscipline.getDiscipline().getId()));
            if (!any) {
                classesDisciplineRepo.delete(classDiscipline);
                remove.add(classDiscipline);
            }
        }
        fromrepo.getDisciplines().removeAll(remove);

        //add elements if repo dont have it
        for (DisciplineDto dtoDiscipline : dto.getDisciplines()) {
            boolean any = fromrepo.getDisciplines().stream()
                    .anyMatch(repoDiscipline -> Objects.equals(repoDiscipline.getDiscipline().getId(), dtoDiscipline.getId()));
            if (!any) {
                ClassDiscipline cd = modelMapper.map(dtoDiscipline, ClassDiscipline.class);
                cd.setClasses(fromrepo);
                fromrepo.getDisciplines().add(cd);
            }
        }
        Classes save = classesRepo.save(fromrepo);
        return modelMapper.map(classesRepo.getById(save.getId()), ClassDto.class);
    }

    @Transactional
    public OperationResponse delete(long id) {
        if (!classesRepo.existsById(id)) {
            return new OperationResponse("Класс не существует");
        }
        Classes classes = classesRepo.getById(id);
        classesRepo.delete(classes);
        return new OperationResponse("Класс удален");
    }

    @Transactional
    public ClassDto getById(long id) {
        if (!classesRepo.existsById(id)) {
            throw new NotFoundException("Класс не существует");
        }
        return modelMapper.map(classesRepo.getById(id), ClassDto.class);
    }

    @Transactional
    public List<ClassDto> getAll() {
        return classesRepo.findAll().stream()
                .map(x -> modelMapper.map(x, ClassDto.class))
                .collect(Collectors.toList());
    }
}

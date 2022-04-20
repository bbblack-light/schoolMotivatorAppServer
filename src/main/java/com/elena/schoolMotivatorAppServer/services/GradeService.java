package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GradeDto;
import com.elena.schoolMotivatorAppServer.model.Grades;
import com.elena.schoolMotivatorAppServer.repo.GradeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private GradeRepo gradeRepo;
    private ChildrenService childrenService;
    private final ModelMapper modelMapper;

    @Autowired
    public GradeService(GradeRepo gradeRepo, ModelMapper modelMapper) {
        this.gradeRepo = gradeRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public GradeDto update(GradeDto dto) {
        Grades d = modelMapper.map(dto, Grades.class);
        return modelMapper.map(gradeRepo.save(d), GradeDto.class);
    }

    @Transactional
    public OperationResponse delete(long id) {
        if (!gradeRepo.existsById(id)) {
            return new OperationResponse("Оценка не существует");
        }
        Grades grades = gradeRepo.getById(id);
        gradeRepo.delete(grades);
        return new OperationResponse("Оценка удалена");
    }

    @Transactional
    public GradeDto getById(long id) {
        if (!gradeRepo.existsById(id)) {
            throw new NotFoundException("Оценка не существует");
        }
        return modelMapper.map(gradeRepo.getById(id), GradeDto.class);
    }

    @Transactional
    public List<GradeDto> getAllByChildId(Long childId) {
        return gradeRepo.findAllByChild(childrenService.getById(childId)).stream()
                .map(x -> modelMapper.map(x, GradeDto.class))
                .collect(Collectors.toList());
    }
}

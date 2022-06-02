package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.Converters;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GoalsDto;
import com.elena.schoolMotivatorAppServer.model.Child;
import com.elena.schoolMotivatorAppServer.model.Goals;
import com.elena.schoolMotivatorAppServer.repo.ChildrenRepo;
import com.elena.schoolMotivatorAppServer.repo.GoalsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalsService {
    private final GoalsRepo goalsRepo;
    private final ChildrenRepo childrenRepo;
    private final ModelMapper modelMapper;
    private final AchievementService achievementService;
    @Autowired
    public GoalsService(GoalsRepo goalsRepo, ChildrenRepo childrenRepo,
                        ModelMapper modelMapper, AchievementService achievementService) {

        this.goalsRepo = goalsRepo;
        this.childrenRepo = childrenRepo;
        this.modelMapper = modelMapper;
        this.achievementService = achievementService;
    }

    @Transactional
    public GoalsDto update(GoalsDto dto, boolean fromDeletedGrade) {
        Goals d = modelMapper.map(dto, Goals.class);
        if (dto.getId()!=null) {
            Optional<Goals> goalFromRepo = goalsRepo.findById(dto.getId());
            if (!goalFromRepo.isPresent()) throw new NotFoundException("Цель не найдена");

            d.setInAchievementProgress(goalFromRepo.get().isInAchievementProgress());
            d.setFirstDateFinished(goalFromRepo.get().getFirstDateFinished());
        }


        Optional<Child> child = childrenRepo.findById(dto.getChildId());
        if (child.isPresent()) d.setChild(child.get());
        else throw new NotFoundException("Ребенок не найден");

        d.setProgress(0);
        d.setFinished(false);

        int c = (int) child.get().getGrades()
                .stream()
                .filter(grades -> grades.getValue() == d.getValue())
                .filter(grades -> d.getDiscipline() == null ||
                        d.getDiscipline().getId().equals(grades.getDiscipline().getId()))
                .filter(grades -> d.getDateStart() == null || d.getDateEnd() == null ||
                        Converters.between(grades.getDate(), d.getDateStart(), d.getDateEnd()))
                .count();

        if (fromDeletedGrade) c--;

        d.setProgress(c);
        if (d.getProgress() >= d.getCountOfGrades()) {
            d.setProgress(d.getCountOfGrades());
            d.setFinished(true);
            if (d.getFirstDateFinished()==null) {
                d.setFirstDateFinished(LocalDate.now());
            }
        }
        Goals goals = goalsRepo.save(d);
        if (goals.isFinished() && goals.getFirstDateFinished().isEqual(LocalDate.now()) && !goals.isInAchievementProgress()) {
            achievementService.UpdateProgress(goals);
            goals.setInAchievementProgress(true);
            goals = goalsRepo.save(goals);
        }

        return modelMapper.map(goals, GoalsDto.class);
    }

    @Transactional
    public OperationResponse delete(long id) {
        if (!goalsRepo.existsById(id)) {
            return new OperationResponse("Оценка не существует");
        }
        Goals grades = goalsRepo.getById(id);
        goalsRepo.delete(grades);
        return new OperationResponse("ok");
    }

    @Transactional
    public GoalsDto getById(long id) {
        if (!goalsRepo.existsById(id)) {
            throw new NotFoundException("Цель не существует");
        }
        return modelMapper.map(goalsRepo.getById(id), GoalsDto.class);
    }

    @Transactional
    public List<GoalsDto> getAllByChild(Long childId) {

        return goalsRepo.findAllByChild(childrenRepo.getById(childId)).stream()
                .map(x -> modelMapper.map(x, GoalsDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GoalsDto> getAllInProgress(Long childId) {
        return goalsRepo
                .findAllByChildAndIsFinished(
                        childrenRepo.getById(childId), false)
                .stream()
                .map(x -> modelMapper.map(x, GoalsDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GoalsDto> getAllFinished(Long childId) {
        return goalsRepo
                .findAllByChildAndIsFinished(
                        childrenRepo.getById(childId), true)
                .stream()
                .map(x -> modelMapper.map(x, GoalsDto.class))
                .collect(Collectors.toList());
    }
}

package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.AchievementDto;
import com.elena.schoolMotivatorAppServer.dto.ChildAchievementDto;
import com.elena.schoolMotivatorAppServer.model.*;
import com.elena.schoolMotivatorAppServer.repo.AchievementRepo;
import com.elena.schoolMotivatorAppServer.repo.ChildAchievementRepo;
import com.elena.schoolMotivatorAppServer.repo.ChildrenRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchievementService {
    private final AchievementRepo achievementRepo;
    private final ChildAchievementRepo childAchievementRepo;
    private final ChildrenRepo childrenRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public AchievementService(AchievementRepo achievementRepo, ChildAchievementRepo childAchievementRepo, ChildrenRepo childrenRepo, ModelMapper modelMapper) {
        this.achievementRepo = achievementRepo;
        this.childAchievementRepo = childAchievementRepo;
        this.childrenRepo = childrenRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AchievementDto update(AchievementDto dto) {
        Achievement d = modelMapper.map(dto, Achievement.class);
        return modelMapper.map(achievementRepo.save(d), AchievementDto.class);
    }

    @Transactional
    public OperationResponse delete(long id) {
        if (!achievementRepo.existsById(id)) {
            return new OperationResponse("Достижение не существует");
        }
        Achievement achievement = achievementRepo.getById(id);
        achievementRepo.delete(achievement);
        return new OperationResponse("Достижение удалено");
    }

    @Transactional
    public void UpdateProgress(Grades grade) {
        // ищем по типу ачивки
        achievementRepo.findAllByType(AchievementType.GRADES).stream()
                //по дисциплине
                .filter(achiv -> achiv.getDiscipline() == null || achiv.getDiscipline().getId().equals(grade.getDiscipline().getId()))
                //по значению оценки
                .filter(achiv -> achiv.getGradesValues() == null || achiv.getGradesValues() == grade.getValue())
                .map(achiv -> {
                    Optional<ChildAchievement> ca = achiv.getChildren().stream()
                            .filter(childAchievement -> childAchievement.getChild().getId().equals(grade.getChild().getId()))
                            .findFirst();
                    ChildAchievement caNew;
                    if (ca.isPresent()) {
                        caNew = ca.get();
                    }
                    else {
                        caNew = new ChildAchievement();
                        caNew.setChild(grade.getChild());
                        caNew.setAchievement(achiv);
                    }
                    return caNew;
                })
                .filter(ca -> !ca.isFinished())
                .forEach(ca -> {
                    ca.setProgress(ca.getProgress()+1);
                    if (ca.getProgress() >= ca.getAchievement().getCountOfGrades()) {
                        ca.setProgress(ca.getAchievement().getCountOfGrades());
                        ca.setDate(LocalDate.now());
                        ca.setFinished(true);
                    }
                    childAchievementRepo.save(ca);
                });
    }


    @Transactional
    public void UpdateProgress(Goals goals) {
        // ищем по типу ачивки
        achievementRepo.findAllByType(AchievementType.GOALS).stream()
                .map(achiv -> {
                    Optional<ChildAchievement> ca = achiv.getChildren().stream()
                            .filter(childAchievement -> childAchievement.getChild().getId().equals(goals.getChild().getId()))
                            .findFirst();
                    ChildAchievement caNew;
                    if (ca.isPresent()) {
                        caNew = ca.get();
                    }
                    else {
                        caNew = new ChildAchievement();
                        caNew.setChild(goals.getChild());
                        caNew.setAchievement(achiv);
                    }
                    return caNew;
                })
                .filter(ca -> !ca.isFinished())
                .forEach(ca -> {
                    ca.setProgress(ca.getProgress()+1);
                    if (ca.getProgress() >= ca.getAchievement().getCountOfGoals()) {
                        ca.setProgress(ca.getAchievement().getCountOfGoals());
                        ca.setDate(LocalDate.now());
                        ca.setFinished(true);
                    }
                    childAchievementRepo.save(ca);
                });
    }

    @Transactional
    public AchievementDto getById(long id) {
        if (!achievementRepo.existsById(id)) {
            throw new NotFoundException("Достижение не существует");
        }
        return modelMapper.map(achievementRepo.getById(id), AchievementDto.class);
    }

    @Transactional
    public List<AchievementDto> getAll() {
        return achievementRepo.findAll().stream()
                .map(x -> modelMapper.map(x, AchievementDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Child addChildAchievement(Child child) {
        achievementRepo.findAllByType(AchievementType.REGISTRATION).stream()
                .map(achiv -> {
                    Optional<ChildAchievement> ca = achiv.getChildren().stream()
                            .filter(childAchievement -> childAchievement.getChild().getId().equals(child.getId()))
                            .findFirst();
                    ChildAchievement caNew;
                    if (ca.isPresent()) {
                        caNew = ca.get();
                    }
                    else {
                        caNew = new ChildAchievement();
                        caNew.setChild(child);
                        caNew.setAchievement(achiv);
                    }
                    return caNew;
                })
                .filter(ca -> !ca.isFinished())
                .forEach(ca -> {
                    ca.setDate(LocalDate.now());
                    ca.setFinished(true);
                    childAchievementRepo.save(ca);
                });
        return child;
    }

    @Transactional
    public OperationResponse distributionOfAchievement(Long achievementId) {
        Optional<Achievement> ao = achievementRepo.findById(achievementId);
        if (!ao.isPresent()) throw new NotFoundException("Достижение не найдено");

        Achievement a = ao.get();
        if (a.getType()!=AchievementType.HANDING) return new OperationResponse("Достижение не раздаваемое");

        childrenRepo.findAll().stream()
                .filter(child -> a.getChildren().stream()
                        .noneMatch(ca -> Objects.equals(ca.getChild().getId(), child.getId())))
                .forEach(child -> {
                    ChildAchievement ca = new ChildAchievement();
                    ca.setAchievement(a);
                    ca.setChild(child);
                    ca.setFinished(true);
                    ca.setDate(LocalDate.now());
                    childAchievementRepo.save(ca);
                });
        return new OperationResponse("Достижения разданы");
    }

    public List<ChildAchievementDto> getAllInPorgressByChildId(Long id) {
        return achievementRepo.findAll().stream()
                .map(achiv -> {
                    Optional<ChildAchievement> ca = achiv.getChildren().stream()
                            .filter(childAchievement -> childAchievement.getChild().getId().equals(id))
                            .findFirst();
                    ChildAchievement caNew;
                    if (ca.isPresent()) {
                        caNew = ca.get();
                    }
                    else {
                        caNew = new ChildAchievement();
                        caNew.setAchievement(achiv);
                        caNew.setProgress(0);
                    }
                    return caNew;
                })
                .filter(ca -> !ca.isFinished())
                .map(ca -> modelMapper.map(ca, ChildAchievementDto.class))
                .collect(Collectors.toList());
    }

    public List<ChildAchievementDto> getAllFinishedByChildId(Long id) {
        return childAchievementRepo.findAllByChildAndIsFinished(childrenRepo.getById(id), true)
                .stream()
                .map(ca -> modelMapper.map(ca, ChildAchievementDto.class))
                .collect(Collectors.toList());
    }
}

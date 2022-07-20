package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.Converters;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GoalsDto;
import com.elena.schoolMotivatorAppServer.dto.GradeDto;
import com.elena.schoolMotivatorAppServer.dto.GradesByWeek;
import com.elena.schoolMotivatorAppServer.model.Child;
import com.elena.schoolMotivatorAppServer.model.Grades;
import com.elena.schoolMotivatorAppServer.repo.ChildrenRepo;
import com.elena.schoolMotivatorAppServer.repo.GradeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private final GradeRepo gradeRepo;
    private final ChildrenRepo childrenRepo;
    private final GoalsService goalsService;
    private final AchievementService achievementService;
    private final EDService edService;
    private final ModelMapper modelMapper;

    @Autowired
    public GradeService(GradeRepo gradeRepo, ChildrenRepo childrenRepo,
                        GoalsService goalsService, AchievementService achievementService,
                        EDService edService, ModelMapper modelMapper) {
        this.gradeRepo = gradeRepo;
        this.goalsService = goalsService;
        this.achievementService = achievementService;
        this.edService = edService;
        this.modelMapper = modelMapper;
        this.childrenRepo = childrenRepo;
    }

    @Transactional
    public GradeDto update(GradeDto dto) throws ParseException {
        Grades d = modelMapper.map(dto, Grades.class);
        Optional<Child> child = childrenRepo.findById(dto.getChildId());
        if (child.isPresent()) d.setChild(child.get());
        else throw new NotFoundException("Ребенок не найден");

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date newWithZeroTime = formatter.parse(formatter.format(dto.getDate()));
        Grades oldGrade = null;
        Date oldWithZeroTime = null;
        if (dto.getId()!=null) {
            oldGrade = gradeRepo.getById(dto.getId());
        }

        Grades newGrade = gradeRepo.save(d);
        if (dto.getId()==null)
            achievementService.UpdateProgress(newGrade);
        Grades finalOldGrade = oldGrade;
        child.get().getGoals().stream()
                //дисциплина
                .filter((goal) -> goal.getDiscipline() == null ||
                        goal.getDiscipline().getId().equals(newGrade.getDiscipline().getId()) ||
                        (finalOldGrade != null && finalOldGrade.getDiscipline().getId().equals(goal.getDiscipline().getId())))
                //дата
                .filter(goals -> goals.getDateStart() == null || goals.getDateEnd() == null ||
                        Converters.between(newGrade.getDate(), goals.getDateStart(), goals.getDateEnd()) ||
                        (finalOldGrade !=null && Converters.between(finalOldGrade.getDate(), goals.getDateStart(), goals.getDateEnd())))
                //редактирование
                .forEach((goals -> {
                    goals.setProgress(goals.getProgress() + 1);
                    goalsService.update(modelMapper.map(goals, GoalsDto.class),false);
                }));

        return modelMapper.map(newGrade, GradeDto.class);
    }

    @Transactional
    public OperationResponse delete(long id) {
        if (!gradeRepo.existsById(id)) {
            return new OperationResponse("Оценка не существует");
        }

        Grades grades = gradeRepo.getById(id);

        gradeRepo.delete(grades);

        grades.getChild().getGoals().stream()
                //дисциплина
                .filter((goal) -> goal.getDiscipline() == null ||
                        goal.getDiscipline().getId().equals(grades.getDiscipline().getId()))
                //дата
                .filter(goals -> goals.getDateStart() == null || goals.getDateEnd() == null ||
                        Converters.between(grades.getDate(), goals.getDateStart(), goals.getDateEnd()))
                .forEach((goals -> {
                    goalsService.update(modelMapper.map(goals, GoalsDto.class), true);
                }));
        return new OperationResponse("ok");
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

        return gradeRepo.findAllByChild(childrenRepo.getById(childId)).stream()
                .map(x -> modelMapper.map(x, GradeDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GradeDto> getLastWeekByChildId(Long childId) {
        return gradeRepo
                .findAllByChildAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(
                        childrenRepo.getById(childId), LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                        LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)))
                .stream()
                .map(x -> modelMapper.map(x, GradeDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public GradesByWeek getByPeriod(Long childId, GradesByWeek dto) throws IOException {
        Child c = childrenRepo.getById(childId);
        if (c.getEDId()!=null && c.getParent().getEdToken()!=null && !c.getParent().getEdToken().getEDToken().equals("")) {
            edService.updateGradesByED(c.getParent(), c, Converters.convertToLocalDateViaInstant(dto.getStart()),
                    Converters.convertToLocalDateViaInstant(dto.getEnd()));
        }

        dto.setGrades(gradeRepo
                .findAllByChildAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(
                        c, Converters.convertToLocalDateViaInstant(dto.getStart()),
                        Converters.convertToLocalDateViaInstant(dto.getEnd()))
                .stream()
                .map(x -> modelMapper.map(x, GradeDto.class))
                .collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public List<GradeDto> getTodayByChildId(Long childId) throws ParseException, IOException {
        Child c = childrenRepo.getById(childId);
        List<GradeDto> res = gradeRepo
                .findAllByChildAndDateOrderById(c, LocalDate.now())
                .stream()
                .map(x -> modelMapper.map(x, GradeDto.class))
                .collect(Collectors.toList());
        if (res.size()!=0) return res;

        if (c.getEDId()!=null && c.getParent().getEdToken()!=null && !c.getParent().getEdToken().getEDToken().equals("")) {
            edService.updateGradesByED(c.getParent(), c, LocalDate.now(), LocalDate.now());
        }

        return gradeRepo
                .findAllByChildAndDateOrderById(c, LocalDate.now())
                .stream()
                .map(x -> modelMapper.map(x, GradeDto.class))
                .collect(Collectors.toList());
    }
}

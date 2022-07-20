package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.Converters;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.InvalidArgumentException;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.exception.TokenExpiredException;
import com.elena.schoolMotivatorAppServer.dto.GoalsDto;
import com.elena.schoolMotivatorAppServer.model.*;
import com.elena.schoolMotivatorAppServer.model.user.User;
import com.elena.schoolMotivatorAppServer.mosRuIntegration.IDnevnikApi;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.GradesByWeek;
import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import com.elena.schoolMotivatorAppServer.mosRuIntegration.models.*;
import com.elena.schoolMotivatorAppServer.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class EDService {
    private final UserRepo userRepo;
    private final ChildrenRepo childrenRepo;
    private final ClassesRepo classesRepo;
    private final GradeRepo gradeRepo;
    private final DisciplinesRepo disciplinesRepo;
    private final IDnevnikApi dnevnikApi;
    private final GoalsService goalsService;
    private final AchievementService achievementService;
    private final ModelMapper modelMapper;

    @Autowired
    public EDService(UserRepo userRepo, ChildrenRepo childrenRepo, ClassesRepo classesRepo, GradeRepo gradeRepo, DisciplinesRepo disciplinesRepo, IDnevnikApi dnevnikApi, GoalsService goalsService, AchievementService achievementService, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.childrenRepo = childrenRepo;
        this.classesRepo = classesRepo;
        this.gradeRepo = gradeRepo;
        this.disciplinesRepo = disciplinesRepo;
        this.dnevnikApi = dnevnikApi;
        this.goalsService = goalsService;
        this.achievementService = achievementService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserDto setTokenAndUpdateInfo(UserToken userToken) throws IOException {
        Optional<User> u = userRepo.findOneByUserId(userToken.getUserId());
        if (!u.isPresent()) throw new NotFoundException("Пользователь не найден");

        if (u.get().getEdToken() == null && u.get().getChildren()!=null &&
                u.get().getChildren().stream().anyMatch(c-> c.getEDId()==null))
            throw new InvalidArgumentException("У пользователя нет возможности подключить электронный дневник");

        User user = u.get();
        user.setEdToken(new EDToken(user, userToken.getToken()));
        userRepo.save(user);
        Response<DUser> response = dnevnikApi.getUserInfoByToken(userToken.getToken()).execute();
        if (response.isSuccessful()) {
            DUser dUser = response.body();
            dUser.getChildren()
                    .forEach(dc -> {
                        Optional<Child> childOptional = user.getChildren().stream().filter(c-> dc.getId() == c.getEDId()).findFirst();
                        Child child = childOptional.orElseGet(Child::new);

                        child.setParent(user);
                        child.setEDId(dc.getId());
                        child.setBirthday(Converters.convertToLocalDateViaInstant(dc.getBirth_date()));
                        child.setFirstName(dc.getFirst_name());
                        child.setLastName(dc.getLast_name());
                        child.setPatronymic(dc.getMiddle_name());
                        Optional<Classes> classesOptional = classesRepo.findByEDId(dc.getClass_unit_id());
                        if (!classesOptional.isPresent()) {
                            Classes classes = new Classes();
                            classes.setEDId(dc.getClass_unit_id());
                            classes.setName(dc.getClass_name());
                            child.setActualClass(classesRepo.saveAndFlush(classes));
                        }
                        else {
                            child.setActualClass(classesOptional.get());
                        }

                        child = childrenRepo.saveAndFlush(child);
                        try {
                            updateGradesByED(user, child, LocalDate.now().minusWeeks(4), LocalDate.now());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!childOptional.isPresent()) user.getChildren().add(child);
                    });

            User userNew = userRepo.findOneByUserId(userToken.getUserId()).get();
            return modelMapper.map(userNew, UserDto.class);
        }
        else {
            throw new TokenExpiredException("Обновите токен МЭШ");
        }
    }

    public OperationResponse updateGradesByED(Long childId, GradesByWeek dates) throws IOException {
        Child c = childrenRepo.getById(childId);
        if (c.getParent()==null) throw new InvalidArgumentException("У ребенка нет родителя");
        if(c.getEDId()==null) throw new InvalidArgumentException("Ребенок не из электронного дневника");
        if(c.getParent().getEdToken()==null || Objects.equals(c.getParent().getEdToken().getEDToken(), "")) throw new InvalidArgumentException("Пользователь не имеет подключенного дневника");

        return updateGradesByED(c.getParent(), c, Converters.convertToLocalDateViaInstant(dates.getStart()), Converters.convertToLocalDateViaInstant(dates.getEnd()));
    }



    @Transactional
    public OperationResponse updateGradesByED(User user, Child child, LocalDate start, LocalDate end) throws IOException {
        if (Objects.isNull(user.getEdToken()) || Objects.equals(user.getEdToken().getEDToken(), ""))
            throw new InvalidArgumentException("Пользователь не имеет подключенного дневника");

        if (Objects.isNull(child.getEDId()))
            throw new InvalidArgumentException("Ребенок не из электронного дневника");

        for (; start.isBefore(end) || start.isEqual(end); start = start.plusDays(1)) {

            Response<Schedule> gED = dnevnikApi.getСhildrenGrades(user.getEdToken().getEDToken(), child.getEDId(), start)
                    .execute();
            if (!gED.isSuccessful()) continue;

            Schedule schedule = gED.body();
            if (schedule.getActivities().size()==0) continue;

            LocalDate finalStart = start;
            schedule.getActivities().stream()
                    .filter(a -> ActivityType.LESSON.equals(a.getType()))
                    .filter(a -> a.getLesson().getMarks().size()!=0)
                    .forEach(a -> {
                        a.getLesson().getMarks().forEach(m -> {
                            saveGrade(a.getLesson(), m, child, finalStart);
                        });
                    });
        }
        child.getGoals().forEach(g ->
                        goalsService.update(modelMapper.map(g, GoalsDto.class), false));

        return new OperationResponse("ok");
    }

    @Transactional
    public void saveGrade(Lesson lesson, Mark mark, Child child, LocalDate date) {
        if (gradeRepo.existsByEDId(mark.getId())) return;
        Grades grade = new Grades();

        try{
            int a = Integer.parseInt(mark.getValue());
            grade.setValue(a);
        }
        catch (NumberFormatException ex){
            return;
        }
        grade.setChild(child);
        grade.setDate(date);
        grade.setEDId(mark.getId());

        if (disciplinesRepo.existsByEDId(lesson.getSubject_id()))
            grade.setDiscipline(disciplinesRepo.findByEDId(lesson.getSubject_id()));
        else if(disciplinesRepo.existsByName(lesson.getSubject_name())) {
            Discipline d = disciplinesRepo.findByName(lesson.getSubject_name());
            d.setEDId(lesson.getSubject_id());
            grade.setDiscipline(disciplinesRepo.save(d));
        }
        else {
            Discipline d = new Discipline();
            d.setName(lesson.getSubject_name());
            d.setEDId(lesson.getSubject_id());
            grade.setDiscipline(disciplinesRepo.save(d));
        }
        grade = gradeRepo.save(grade);
        achievementService.UpdateProgress(grade);
    }
}

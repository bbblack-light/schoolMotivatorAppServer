package com.elena.schoolMotivatorAppServer.config;

import com.elena.schoolMotivatorAppServer.dto.*;
import com.elena.schoolMotivatorAppServer.dto.user.UserDto;
import com.elena.schoolMotivatorAppServer.model.*;
import com.elena.schoolMotivatorAppServer.repo.ChildrenRepo;
import com.elena.schoolMotivatorAppServer.repo.ClassesDisciplineRepo;
import com.elena.schoolMotivatorAppServer.repo.DisciplinesRepo;
import com.elena.schoolMotivatorAppServer.repo.UserRepo;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {
    private final ClassesDisciplineRepo classesDisciplineRepo;
    private final UserRepo userRepo;
    private final DisciplinesRepo disciplinesRepo;
    private final ChildrenRepo childrenRepo;

    @Autowired
    public ModelMapperConfiguration(ClassesDisciplineRepo classesDisciplineRepo, UserRepo userRepo, DisciplinesRepo disciplinesRepo, ChildrenRepo childrenRepo) {
        this.classesDisciplineRepo = classesDisciplineRepo;
        this.userRepo = userRepo;
        this.disciplinesRepo = disciplinesRepo;
        this.childrenRepo = childrenRepo;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        configureClassMappings(modelMapper);
        configureChildrenMappings(modelMapper);
        configureGradeMappings(modelMapper);
        return modelMapper;
    }

    private void configureChildrenMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(ChildDto.class, Child.class).addMappings(mapper -> {
            mapper.map(ChildDto::getParent, (Child child, UserDto userDto) -> {
                if (userDto!=null) userRepo.findOneByUserId(userDto.getUserId()).ifPresent(child::setParent);
                else child.setParent(null);
            });
        });
    }


    private void configureGradeMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(GradeDto.class, Grades.class).addMappings(mapper -> {
            mapper.map(GradeDto::getDiscipline, (Grades grades, DisciplineDto disciplineDto) -> {
                if (disciplineDto!=null) {
                    Optional<Discipline> d = disciplinesRepo.findById(disciplineDto.getId());
                    if (d.isPresent()) {
                        grades.setDiscipline(d.get());
                        return;
                    }
                }
                grades.setDiscipline(null);
            });
            mapper.map(GradeDto::getChildId, (Grades grades, Long childId) -> {
                if (childId!=null) {
                    Optional<Child> d = childrenRepo.findById(childId);
                    if (d.isPresent()) {
                        grades.setChild(d.get());
                        return;
                    }
                }
                grades.setChild(null);
            });
        });
    }

    private void configureClassMappings(ModelMapper modelMapper) {

        modelMapper.typeMap(Classes.class, ClassDto.class).addMappings(mapper -> {

            Converter<List<ClassDiscipline>, List<DisciplineDto>> collectionToSize = c -> (List) c.getSource().stream()
                    .map(discipline -> modelMapper.map(discipline.getDiscipline(), DisciplineDto.class))
                    .collect(Collectors.toList());

            mapper.using(collectionToSize)
                    .map(Classes::getDisciplines, ClassDto::setDisciplines);
        });

        modelMapper.typeMap(DisciplineDto.class, ClassDiscipline.class).addMappings(mapper -> {
            Converter<DisciplineDto, Discipline> collectionToSize = mappingContext ->  modelMapper.map(mappingContext.getSource(), Discipline.class);

            mapper.using(collectionToSize)
                    .map(disciplineDto -> disciplineDto, ClassDiscipline::setDiscipline);
            mapper.skip(ClassDiscipline::setId);
        });

        modelMapper.typeMap(ClassDto.class, Classes.class).addMappings(mapper -> {
            mapper.map(ClassDto::getDisciplines,
                    (Classes classes, List<DisciplineDto> disciplines) -> {
                        if (disciplines == null) {
                            classes.setDisciplines(null);
                            return;
                        }
                        classes.setDisciplines(disciplines.stream()
                                .map(disciplineDto -> {
                                    ClassDiscipline cd = modelMapper.map(disciplineDto, ClassDiscipline.class);
                                    cd.setClasses(classes);
                                    cd.setId(null);
                                    classesDisciplineRepo.findByClassesIdAndDisciplineId(classes.getId(), cd.getDiscipline().getId())
                                            .ifPresent(cdrep -> {
                                                cd.setId(cdrep.getId());
                                            });
                                    return cd;
                                })
                                .collect(Collectors.toList()));
                    }
            );
        });
    }

    private void configureAchievementMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(Achievement.class, AchievementDto.class).addMappings(mapper -> {

            mapper.map(Achievement::getChildren, (AchievementDto dto, List<ChildAchievement> children) ->
                    dto.setChildrenCount( CollectionUtils.isEmpty(children) ? 0 : children.size()) );

        });
    }

}

package com.elena.schoolMotivatorAppServer.config;

import com.elena.schoolMotivatorAppServer.dto.buisness.ClassDto;
import com.elena.schoolMotivatorAppServer.dto.buisness.DisciplineDto;
import com.elena.schoolMotivatorAppServer.model.buisness.ClassDiscipline;
import com.elena.schoolMotivatorAppServer.model.buisness.Classes;
import com.elena.schoolMotivatorAppServer.model.buisness.Discipline;
import com.elena.schoolMotivatorAppServer.repo.ClassesDisciplineRepo;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {
    private final ClassesDisciplineRepo classesDisciplineRepo;

    @Autowired
    public ModelMapperConfiguration(ClassesDisciplineRepo classesDisciplineRepo) {
        this.classesDisciplineRepo = classesDisciplineRepo;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        configureClassMappings(modelMapper);
        return modelMapper;
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

}

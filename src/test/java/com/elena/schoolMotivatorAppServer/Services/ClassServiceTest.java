package com.elena.schoolMotivatorAppServer.Services;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.buisness.ClassDto;
import com.elena.schoolMotivatorAppServer.dto.buisness.DisciplineDto;
import com.elena.schoolMotivatorAppServer.services.ClassService;
import com.elena.schoolMotivatorAppServer.services.DisciplineService;
import com.google.common.base.Objects;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClassServiceTest {
    @Autowired
    private ClassService classService;
    @Autowired
    private DisciplineService disciplineService;

    @Test
    public void update() {
        List<DisciplineDto> disciplines = new ArrayList<>();
        DisciplineDto dto1 = new DisciplineDto();
        dto1.setDescription("наука всех наук");
        dto1.setName("математика");
        dto1 = disciplineService.update(dto1);
        disciplines.add(dto1);

        DisciplineDto dto2 = new DisciplineDto();
        dto2.setDescription("великий и могучий");
        dto2.setName("русский язык");
        dto2 = disciplineService.update(dto2);
        disciplines.add(dto2);

        DisciplineDto dto3 = new DisciplineDto();
        dto3.setDescription("наука о природе и жизни");
        dto3.setName("биология");
        dto3 = disciplineService.update(dto3);

        ClassDto classDto = new ClassDto();
        classDto.setDisciplines(disciplines);
        classDto.setDescription("9 класс лучший");
        classDto.setName("9 класс");

        classDto = classService.update(classDto);
        Assert.assertEquals(2, classDto.getDisciplines().size());

        DisciplineDto finalDto1 = dto1;
        classDto.getDisciplines().removeIf(d -> Objects.equal(d.getId(), finalDto1.getId()));
        classDto.getDisciplines().add(dto3);
        classDto = classService.update(classDto);
        Assert.assertEquals(2, classDto.getDisciplines().size());

        classDto.getDisciplines().add(dto1);
        classDto = classService.update(classDto);
        Assert.assertEquals(3, classDto.getDisciplines().size());
    }

    @Test
    public void delete() {
        ClassDto classDto = new ClassDto();
        classDto.setDescription("9 класс лучший");
        classDto.setName("9 класс");

        classDto = classService.update(classDto);

        OperationResponse response = classService.delete(classDto.getId());
        Assert.assertEquals("Класс удален", response.getMessage());
    }

    @Test
    public void getAll() {
        List<DisciplineDto> disciplines = new ArrayList<>();
        DisciplineDto dto1 = new DisciplineDto();
        dto1.setDescription("наука всех наук");
        dto1.setName("математика");
        dto1 = disciplineService.update(dto1);
        disciplines.add(dto1);

        DisciplineDto dto2 = new DisciplineDto();
        dto2.setDescription("великий и могучий");
        dto2.setName("русский язык");
        dto2 = disciplineService.update(dto2);
        disciplines.add(dto2);

        ClassDto classDto = new ClassDto();
        classDto.setDescription("9 класс лучший");
        classDto.setName("9 класс");
        classDto.setDisciplines(disciplines);

        classDto = classService.update(classDto);

        ClassDto classDto2 = new ClassDto();
        classDto2.setDescription("10 класс лучший");
        classDto2.setName("10 класс");

        classDto2 = classService.update(classDto2);

        List<ClassDto> classes = classService.getAll();
        Assert.assertEquals(2, classes.size());
        ClassDto finalClassDto = classDto;
        Assert.assertEquals(2,
                classes.stream()
                .filter(classDto1 -> Objects.equal(classDto1.getId(), finalClassDto.getId()))
                .findFirst().get()
                .getDisciplines().size());
    }

    @Test
    public void getById() {
        List<DisciplineDto> disciplines = new ArrayList<>();
        DisciplineDto dto1 = new DisciplineDto();
        dto1.setDescription("наука всех наук");
        dto1.setName("математика");
        dto1 = disciplineService.update(dto1);
        disciplines.add(dto1);

        DisciplineDto dto2 = new DisciplineDto();
        dto2.setDescription("великий и могучий");
        dto2.setName("русский язык");
        dto2 = disciplineService.update(dto2);
        disciplines.add(dto2);

        ClassDto classDto = new ClassDto();
        classDto.setDescription("9 класс лучший");
        classDto.setName("9 класс");
        classDto.setDisciplines(disciplines);

        classDto = classService.update(classDto);

        ClassDto classes = classService.getById(classDto.getId());
        Assert.assertEquals(2, classes.getDisciplines().size());
        Assert.assertEquals(classDto.getId(), classes.getId());
    }
}
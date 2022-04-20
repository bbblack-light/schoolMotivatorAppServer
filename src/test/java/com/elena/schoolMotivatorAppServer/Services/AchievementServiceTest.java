package com.elena.schoolMotivatorAppServer.Services;

import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.AchievementDto;
import com.elena.schoolMotivatorAppServer.services.AchievementService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class AchievementServiceTest {
    @Autowired
    private AchievementService achievementService;

    @Test
    public void update() {
        AchievementDto dto = new AchievementDto();
        dto.setDescription("ачивка лучше всех");
        dto.setBase64("");
        dto.setName("Прилежный ученик");

        AchievementDto newDto = achievementService.update(dto);
        assertNotNull(newDto);
        achievementService.delete(newDto.getId());
    }

    @Test
    public void delete() {
        AchievementDto dto = new AchievementDto();
        dto.setDescription("ачивка лучше всех");
        dto.setBase64("");
        dto.setName("Прилежный ученик");

        dto = achievementService.update(dto);

        OperationResponse response = achievementService.delete(dto.getId());
        Assert.assertEquals("Достижение удалено", response.getMessage());
    }

    @Test
    public void getAll() {
        AchievementDto dto = new AchievementDto();
        dto.setDescription("ачивка лучше всех");
        dto.setBase64("");
        dto.setName("Прилежный ученик");

        AchievementDto newDto = achievementService.update(dto);

        AchievementDto dto1 = new AchievementDto();
        dto.setDescription("ачивка лучше всех");
        dto.setBase64("");
        dto.setName("Прилежный ученик");

        AchievementDto newDto1 = achievementService.update(dto);

        AchievementDto dto2 = new AchievementDto();
        dto.setDescription("ачивка лучше всех");
        dto.setBase64("");
        dto.setName("Прилежный ученик");

        AchievementDto newDto2 = achievementService.update(dto);

        List<AchievementDto> dtos = achievementService.getAll();
        Assert.assertNotEquals(0, dtos.size());

        achievementService.delete(newDto.getId());
        achievementService.delete(newDto1.getId());
        achievementService.delete(newDto2.getId());
    }

    @Test
    public void getById() {
        AchievementDto dto = new AchievementDto();
        dto.setDescription("ачивка лучше всех");
        dto.setBase64("");
        dto.setName("Прилежный ученик");

        AchievementDto newDto = achievementService.update(dto);

        dto = achievementService.getById(newDto.getId());

        assertEquals(dto.getId(), newDto.getId());
        assertEquals(dto.getName(), newDto.getName());
        assertEquals(dto.getBase64(), newDto.getBase64());
        assertEquals(dto.getChildrenCount(), newDto.getChildrenCount());

        achievementService.delete(newDto.getId());
    }

}

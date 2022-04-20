package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.AchievementDto;
import com.elena.schoolMotivatorAppServer.model.Achievement;
import com.elena.schoolMotivatorAppServer.repo.AchievementRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {
    private final AchievementRepo achievementRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public AchievementService(AchievementRepo achievementRepo, ModelMapper modelMapper) {
        this.achievementRepo = achievementRepo;
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
}

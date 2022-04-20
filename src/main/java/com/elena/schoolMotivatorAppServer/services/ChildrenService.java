package com.elena.schoolMotivatorAppServer.services;

import com.elena.schoolMotivatorAppServer.controllers.utils.exception.NotFoundException;
import com.elena.schoolMotivatorAppServer.controllers.utils.response.OperationResponse;
import com.elena.schoolMotivatorAppServer.dto.ChildDto;
import com.elena.schoolMotivatorAppServer.model.Child;
import com.elena.schoolMotivatorAppServer.model.user.User;
import com.elena.schoolMotivatorAppServer.repo.ChildrenRepo;
import com.elena.schoolMotivatorAppServer.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChildrenService {
    private ChildrenRepo childrenRepo;
    private ModelMapper modelMapper;
    private UserRepo userRepo;

    @Autowired
    public ChildrenService(ChildrenRepo childrenRepo, ModelMapper modelMapper, UserRepo userRepo) {
        this.childrenRepo = childrenRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }

    @Transactional
    public ChildDto update(ChildDto dto) {
        Child child = modelMapper.map(dto, Child.class);
        return modelMapper.map(childrenRepo.save(child), ChildDto.class);
    }

    @Transactional
    public OperationResponse delete(long id) {
        if (!childrenRepo.existsById(id)) {
            return new OperationResponse("Ребенок не существует");
        }
        Child child = childrenRepo.getById(id);
        childrenRepo.delete(child);
        return new OperationResponse("Ребенок удален");
    }

    @Transactional
    public ChildDto getById(long id) {
        if (!childrenRepo.existsById(id)) {
            throw new NotFoundException("Ребенок не существует");
        }
        return modelMapper.map(childrenRepo.getById(id), ChildDto.class);
    }

    @Transactional
    public List<ChildDto> getAll() {
        return childrenRepo.findAll().stream()
                .map(x -> modelMapper.map(x, ChildDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ChildDto> getAllByUser(String userId) {
        Optional<User> u = userRepo.findOneByUserId(userId);
        if (!u.isPresent()) {
            throw new NotFoundException("Пользователь не существует");
        }
        return childrenRepo.findAllByParent(u.get()).stream()
                .map(x -> modelMapper.map(x, ChildDto.class))
                .collect(Collectors.toList());
    }
}

package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.dto.ChildDto;
import com.elena.schoolMotivatorAppServer.model.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grades, Long> {
    List<Grades> findAllByChild(ChildDto byId);
}

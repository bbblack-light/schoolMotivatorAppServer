package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.Child;
import com.elena.schoolMotivatorAppServer.model.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grades, Long> {
    List<Grades> findAllByChild(Child byId);

    List<Grades> findAllByChildAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(Child child, LocalDate start, LocalDate end);

    List<Grades> findAllByChildAndDateOrderById(Child child, LocalDate date);
}

package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.buisness.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepo extends JpaRepository<Grades, Long> {
}
package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.Discipline;
import com.elena.schoolMotivatorAppServer.model.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinesRepo extends JpaRepository<Discipline, Long> {
    Discipline findByEDId(Long id);
    boolean existsByEDId(Long id);

    boolean existsByName(String name);
    Discipline findByName(String name);
}

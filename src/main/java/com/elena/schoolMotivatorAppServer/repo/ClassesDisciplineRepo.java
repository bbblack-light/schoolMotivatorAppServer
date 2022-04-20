package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.ClassDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesDisciplineRepo extends JpaRepository<ClassDiscipline, Long> {

    Optional<ClassDiscipline> findByClassesIdAndDisciplineId(Long ClassId, Long DisciplineId);
}

package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.Child;
import com.elena.schoolMotivatorAppServer.model.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GoalsRepo extends JpaRepository<Goals, Long> {
    List<Goals> findAllByChild(Child byId);

    List<Goals> findAllByChildAndIsFinished(Child byId, boolean b);
}

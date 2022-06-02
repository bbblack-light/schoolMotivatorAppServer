package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.Child;
import com.elena.schoolMotivatorAppServer.model.ChildAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildAchievementRepo extends JpaRepository<ChildAchievement, Long> {
    List<ChildAchievement> findAllByChildAndIsFinished(Child child, boolean isFinished);
}

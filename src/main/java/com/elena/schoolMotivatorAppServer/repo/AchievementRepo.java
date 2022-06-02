package com.elena.schoolMotivatorAppServer.repo;

import com.elena.schoolMotivatorAppServer.model.Achievement;
import com.elena.schoolMotivatorAppServer.model.AchievementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AchievementRepo extends JpaRepository<Achievement, Long> {
    List<Achievement> findAllByType(AchievementType type);
}

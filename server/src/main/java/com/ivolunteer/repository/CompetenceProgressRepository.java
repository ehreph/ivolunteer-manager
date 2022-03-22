package com.ivolunteer.repository;

import com.ivolunteer.domain.ActivityProgress;
import com.ivolunteer.domain.CompetenceProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Competence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenceProgressRepository extends JpaRepository<CompetenceProgress, Long> {
//    List<ActivityProgress> findAllByActivityIdAndUserIdAndDateBetween(Long activityId, Long userId, Instant actStartDate, Instant actEndDate);
}

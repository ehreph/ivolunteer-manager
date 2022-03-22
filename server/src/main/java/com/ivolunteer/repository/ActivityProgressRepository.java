package com.ivolunteer.repository;

import com.ivolunteer.domain.Activity;
import com.ivolunteer.domain.ActivityProgress;
import com.ivolunteer.user.dto.ActivityProgressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Competence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityProgressRepository extends JpaRepository<ActivityProgress, Long> {
  List<ActivityProgress> findAllByActivityIdAndUserIdAndDateBetween(Long activityId, Long userId, Instant actStartDate,
                                                                    Instant actEndDate);

  @Query("SELECT COALESCE(SUM(ap.value),0) FROM ActivityProgress ap" +
    " WHERE ap.activity.id=:activityId AND ap.user.id= :userId" +
    " AND ap.date BETWEEN :actStartDate AND :actEndDate")
  Double selectProgressSumOfUserForActivity(@Param("activityId") Long activityId,
                                                            @Param("userId") Long userId,
                                                            @Param("actStartDate") Instant actStartDate,
                                                            @Param("actEndDate") Instant actEndDate);
}

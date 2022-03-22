package com.ivolunteer.repository;

import com.ivolunteer.domain.Badge;
import com.ivolunteer.domain.Goal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Goal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query(value = "SELECT goal FROM Goal goal " +
        " WHERE goal.id not in (:subscribedGoals) and goal.isPersonal=false")
    Page<Goal> findAllGoalsNotSubscribedByUser(@Param("subscribedGoals") List<Long> subscribedGoals, Pageable pageable);

    @Query(value = "SELECT goal FROM Goal goal " +
        " WHERE goal.isPersonal=false")
    Page<Goal> findAllVisibleGoals(Pageable pageable);


  @Query("SELECT g FROM Goal g" +
    " WHERE g.id not in ( SELECT ug.goal.id FROM UserGoals ug where ug.user.id =:userId)")
  List<Goal> findAllNewGoalsForUser(@Param("userId") Long userId);


}

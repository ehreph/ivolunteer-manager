package com.ivolunteer.repository;

import com.ivolunteer.domain.UserGoals;

import com.ivolunteer.user.dto.UserGoalsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserGoals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGoalsRepository extends JpaRepository<UserGoals, Long> {
    Page<UserGoals> findAllByUserIdAndFinishedFalse(Long userId, Pageable pageable);

    Page<UserGoals> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT ug.goal_id FROM user_goals ug WHERE ug.user_id=:userId ", nativeQuery = true)
    List<Long> findAllGoalIdsByUserId(@Param("userId") Long userId);

    List<UserGoals> findAllByUserIdAndFinishedTrue(Long userId);

    Long countAllByUserIdAndFinishedFalse(Long userId);

    @Query("SELECT NEW  com.ivolunteer.user.dto.UserGoalsDTO(ug.id, ug.user.id, ug.goal.id,ug.goal.name,ug.finished) " +
        " FROM UserGoals ug where ug.user.id =:userId and ug.goal.id IN :goalIds")
    List<UserGoalsDTO> findAllByUserIdAndGoalIdIn(@Param("userId") Long id, @Param("goalIds") List<Long> badgeIds);

    Optional<UserGoals> findByIdAndUserId(Long id, Long userId);

    Boolean existsByGoalIdAndUserId(Long goalId, Long id);



}

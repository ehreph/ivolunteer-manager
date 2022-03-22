package com.ivolunteer.repository;

import com.ivolunteer.domain.GoalAward;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GoalAward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalAwardRepository extends JpaRepository<GoalAward, Long> {
}

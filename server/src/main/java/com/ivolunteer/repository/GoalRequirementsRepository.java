package com.ivolunteer.repository;

import com.ivolunteer.domain.GoalRequirements;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GoalRequirements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalRequirementsRepository extends JpaRepository<GoalRequirements, Long> {
}

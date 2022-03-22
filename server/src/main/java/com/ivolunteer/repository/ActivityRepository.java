package com.ivolunteer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivolunteer.domain.Activity;
import com.ivolunteer.domain.Competence;

/**
 * Spring Data  repository for the Competence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}

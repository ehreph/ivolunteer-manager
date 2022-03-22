package com.ivolunteer.repository;

import com.ivolunteer.domain.Institution;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Institution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}

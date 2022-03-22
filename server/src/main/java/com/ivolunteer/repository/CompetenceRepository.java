package com.ivolunteer.repository;

import com.ivolunteer.domain.Competence;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Competence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

  @Query("SELECT c FROM Competence c" +
    " WHERE c.id not in ( SELECT uc.comp.id FROM UserCompetence uc where uc.user.id =:userId)")
  List<Competence> findAllNewCompetencesForUser(@Param("userId") Long userId);
}

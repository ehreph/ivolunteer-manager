package com.ivolunteer.service;

import com.ivolunteer.service.dto.CompetenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.Competence}.
 */
public interface CompetenceService {

    /**
     * Save a competence.
     *
     * @param competenceDTO the entity to save.
     * @return the persisted entity.
     */
    CompetenceDTO save(CompetenceDTO competenceDTO);

    /**
     * Get all the competences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetenceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" competence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetenceDTO> findOne(Long id);

    /**
     * Delete the "id" competence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

  List<CompetenceDTO> findNewCompetencesForUser(Long userId);
}

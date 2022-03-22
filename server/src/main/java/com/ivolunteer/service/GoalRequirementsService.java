package com.ivolunteer.service;

import com.ivolunteer.service.dto.GoalRequirementsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.GoalRequirements}.
 */
public interface GoalRequirementsService {

    /**
     * Save a goalRequirements.
     *
     * @param goalRequirementsDTO the entity to save.
     * @return the persisted entity.
     */
    GoalRequirementsDTO save(GoalRequirementsDTO goalRequirementsDTO);

    /**
     * Get all the goalRequirements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoalRequirementsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" goalRequirements.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoalRequirementsDTO> findOne(Long id);

    /**
     * Delete the "id" goalRequirements.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

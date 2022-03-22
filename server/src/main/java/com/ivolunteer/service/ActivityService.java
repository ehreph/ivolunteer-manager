package com.ivolunteer.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.service.dto.CompetenceDTO;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.Competence}.
 */
public interface ActivityService {

    /**
     * Save a competence.
     *
     * @param activityDTO the entity to save.
     * @return the persisted entity.
     */
    ActivityDTO save(ActivityDTO activityDTO);

    /**
     * Get all the activities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" competence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityDTO> findOne(Long id);

    /**
     * Delete the "id" competence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
